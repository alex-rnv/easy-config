package com.alexrnv.easyconf.thirdparty;

import com.alexrnv.easyconf.Module;
import com.amazonaws.regions.Regions;
import com.alexrnv.easyconf.entity.ValueResolver;

/**
 * This is example of externally configurable module to enhance basic capabilities.
 * @author Alex
 */
public class AwsModule extends Module {
    public AwsModule() {
        super("Aws");
        addResolver("aws_region_id", new AwsRegionResolver());
    }

    public static class AwsRegionResolver implements ValueResolver {
        @Override
        public Object resolveValue(Object val) {
            //fails fast for wrong values
            return Regions.fromName(val.toString());
        }
    }
}
