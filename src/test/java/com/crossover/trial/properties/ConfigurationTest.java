package com.crossover.trial.properties;

import com.crossover.trial.properties.entity.Property;
import com.crossover.trial.properties.thirdparty.AwsModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Date: 11/22/2015
 * Time: 7:46 PM
 *
 * @author Alex
 */
public class ConfigurationTest {

    HttpServer server;
    Path path;

    @Before
    public void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8113), 0);
        server.createContext("/test/config.json", new Handler());
        server.setExecutor(null);
        server.start();

        path = Files.createFile(Paths.get(System.getProperty("java.io.tmpdir"), "aws-tmp.properties"));
        try(BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("aws_access_key=AKIAJSF6XRIJNJTTTL3Q\n" +
                    "aws_secret_key=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\n" +
                    "aws_account_id=12345678\n" +
                    "aws_region_id=us-east-1");
            writer.flush();
        }
    }

    @After
    public void tearDown() {
        if(server != null) {
            server.stop(0);
        }
        if(path != null) {
            try {
                Files.delete(path);
            } catch (IOException e) {
            }
        }
    }

    /**
     * Ignore the test at build machine, because of possible http server problems.
     * It works locally.
     * @throws IOException
     */
    @Ignore
    public void regressionTest() throws IOException {
        Configuration configuration = new Configuration();
        configuration.addModule(new AwsModule());
        configuration.init();

        configuration.addSource("file://" + path.toString());
        configuration.addSource("http://localhost:8112/test/config.json");
        configuration.addSource("classpath:resources/jdbc.properties");

        Collection<Property> list = configuration.getAllProperties();
        System.out.println(list);
        Assert.assertEquals(17, list.size());
    }


    static class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "{\n" +
                    "  \"auth.endpoint.uri\": \"https://authserver/v1/auth\",\n" +
                    "  \"job.timeout\": 3600,\n" +
                    "  \"job.maxretry\": 4,\n" +
                    "  \"sns.broadcast.topic_name\": \"broadcast\",\n" +
                    "  \"sns.broadcast.visibility_timeout\": 30,\n" +
                    "  \"score.factor\": 2.4,\n" +
                    "  \"jpa.showSql\": false\n" +
                    "}";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}