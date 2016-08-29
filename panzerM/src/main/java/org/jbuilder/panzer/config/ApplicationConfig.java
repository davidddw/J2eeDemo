package org.jbuilder.panzer.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.jbuilder.panzer.util.AuthRequestFilter;
import org.jbuilder.panzer.util.GsonJerseyProvider;

public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        //设置scan路径
        packages("org.jbuilder.panzer.action");
        //注册gson工具类
        register(GsonJerseyProvider.class);
        //注册secure filter
        register(AuthRequestFilter.class);
        property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
    }
}
