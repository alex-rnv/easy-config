package com.alexrnv.easyconf;


import com.alexrnv.easyconf.thirdparty.AwsModule;

import java.io.IOException;

/**
 * Trial application entry point. Demonstrates configuration engine use case.
 * @author Alex
 */
public class Main {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addModule(new AwsModule());
        configuration.init();

        for(String arg : args) {
            try {
                configuration.addSource(arg);
            } catch (IOException e) {
                //generally it is better to exit here, but this is just sample app
                System.err.println("Failed to load source " + arg);
            }
        }

        //as per task requirements, output should be sorted.
        //there is not need to keep properties sorted in real case, so we sort them here.
        configuration.getAllProperties().stream()
                .sorted((p1, p2) -> String.CASE_INSENSITIVE_ORDER.compare(p1.getName(), p2.getName()))
                .forEach(p -> System.out.println(
                        p.getName() + ", " + p.getValue().getClass().getName() + ", " + p.getValue())
                );
    }
}
