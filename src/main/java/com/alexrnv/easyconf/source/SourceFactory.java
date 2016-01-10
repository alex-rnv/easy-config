package com.alexrnv.easyconf.source;

import com.alexrnv.easyconf.entity.PropertyFactory;
import org.slf4j.Logger;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Creates sources based on input source URL. Following url prefixes allowed:<br/>
 * <ul>
 *     <li><b>classpath:resources/</b> - for files in classpath</li>
 *     <li><b>file:///</b> - for files in local file system</li>
 *     <li><b>http(s):///</b> - for remote files available via http</li>
 * </ul>
 * Following extensions supported:<br/>
 * <ul>
 *     <li><b>.properties</b> - standard property file</li>
 *     <li><b>.json</b> - json configuration (embedded types and arrays are ignored in this version)</li>
 * </ul>
 * @author Alex
 */
public class SourceFactory {
    private static final Logger LOG = getLogger(SourceFactory.class);

    private static final String RESOURCES_PREF = "classpath:resources/";
    private static final String FILE_PREF = "file://";
    private static final String HTTP_PREF = "http://";
    private static final String HTTPS_PREF = "https://";
    private static final String PROPS_SUF = ".properties";
    private static final String JSON_SUF = ".json";

    private final PropertyFactory propertyFactory;

    public SourceFactory(PropertyFactory propertyFactory) {
        this.propertyFactory = requireNonNull(propertyFactory);
    }

    public Source newSource(String path) {
        requireNonNull(path);
        LOG.debug("New source candidate: " + path);

        StreamReader reader = getReader(path);

        if(path.startsWith(RESOURCES_PREF)) {
            return new ClasspathSource(path.substring(RESOURCES_PREF.length()), reader);
        }
        if(path.startsWith(FILE_PREF)) {
            return new FileSource(path.substring(FILE_PREF.length()), reader);
        }
        if(path.startsWith(HTTP_PREF) || path.startsWith(HTTPS_PREF)) {
            return new HttpSource(path, reader);
        }

        throw new IllegalArgumentException("Unknown prefix '" + path +
                "', only 'file://', 'http(s)://', " + "'classpath:resources/' prefixes supported");
    }

    StreamReader getReader(String path) {
        if (path.endsWith(PROPS_SUF)) {
            return new PropFileStreamReader(propertyFactory);
        } else if (path.endsWith(JSON_SUF)) {
            return new JsonFileStreamReader(propertyFactory);
        }
        throw new IllegalArgumentException("Only '.properties' and '.json' extensions supported");

    }
}
