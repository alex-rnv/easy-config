package com.crossover.trial.properties.entity;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * <div>
 *     Applies following rules sequentially:<ul>
 *         <li>If input value is null or not java.lang.String, return it</li>
 *         <li>If input string matches (true|false), case ignored, cast it to Boolean./li>
 *         <li>If input string is number, according to {@link NumberUtils#isNumber(java.lang.String)},
 *         return one of java numeric types./li>
 *         <li>If input string offset iso time format (e.g. 2011-12-03T10:15:30+01:00), it is cast to
 *         java.time.Instant/li>
 *     </ul>
 * </div>
 * @author Alex
 */
class DefaultValueResolver implements ValueResolver {
    private static final Logger LOG = getLogger(DefaultValueResolver.class);

    @Override
    public Object resolveValue(Object obj) {

        if(!(obj instanceof String)) {
            //assume it is already resolved
            return obj;
        }

        String val = (String)obj;

        if(isBooleanStr(val)) {
            return Boolean.parseBoolean(val);
        }
        if(NumberUtils.isNumber(val)) {
            return NumberUtils.createNumber(val);
        }
        Instant i;
        if((i = getInstant(val)) != null) {
            return i;
        }
        return val;
    }

    private static Instant getInstant(String str) {
        try {
            Instant i = OffsetDateTime.parse(str).toInstant();
            LOG.debug(str + " resolved to Instant");
            return i;
        } catch (DateTimeException e) {
            LOG.debug(str + " does not match to OffsetDateTime pattern");
        }
        return null;
    }

    private static final Pattern isBoolean = Pattern.compile("(true|false)", Pattern.CASE_INSENSITIVE);

    private static boolean isBooleanStr(String val) {
        return val != null && isBoolean.matcher(val).matches();
    }
}
