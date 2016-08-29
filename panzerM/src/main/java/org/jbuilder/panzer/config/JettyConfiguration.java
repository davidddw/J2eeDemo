package org.jbuilder.panzer.config;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.io.IOException;

@Configuration
public class JettyConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${jetty.port:8080}")
    private int jettyPort;

    @Bean
    public ServletContextHandler jettyWebAppContext() throws IOException {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(applicationConfig));
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(jerseyServlet, "/rest/*");

        /*
         * Create the root web application context and set it as a servlet
         * attribute so the dispatcher servlet can find it.
         */
        GenericWebApplicationContext webApplicationContext = new GenericWebApplicationContext();
        webApplicationContext.setParent(applicationContext);
        webApplicationContext.refresh();
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, webApplicationContext);
        context.setErrorHandler(createErrorHandler());
        return context;
    }

    @Bean
    public ErrorHandler createErrorHandler() {
        ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
        errorHandler.addErrorPage(404, "/errorpage");
        return errorHandler;
    }

    /**
     * Jetty Server bean.
     * <p/>
     * Instantiate the Jetty server.
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server jettyServer() throws IOException {

        /* Create the server. */
        Server server = new Server();

        /* Create a basic connector. */
        ServerConnector httpConnector = new ServerConnector(server);
        httpConnector.setPort(jettyPort);
        server.addConnector(httpConnector);

        server.setHandler(jettyWebAppContext());

        return server;
    }
}
