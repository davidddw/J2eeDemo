package org.jbuilder.panzer;

import org.jbuilder.panzer.config.RootConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.GenericWebApplicationContext;

public class Application {

    /**
     * Flag that will be set to true when the web application context
     * (SpringMVC) is refreshed.
     */
    static boolean webApplicationContextInitialized = false;

    public static void jettyServer() {
        final Logger logger = LoggerFactory.getLogger("Application");

        //使用配置类来管理配置文件
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        try {

            applicationContext.addApplicationListener((ContextRefreshedEvent event) -> {
                    ApplicationContext ctx = event.getApplicationContext();
                if (ctx instanceof GenericWebApplicationContext) {
                    webApplicationContextInitialized = true;
                }
            });

            //注册关闭钩子, 确保你的Spring IoC容器被恰当关闭，以及所有由单例持有的资源都会被释放
            applicationContext.registerShutdownHook();
            //注册之前定义好的root类
            applicationContext.register(RootConfiguration.class);
            applicationContext.refresh();

            if (!webApplicationContextInitialized) {
                logger.error("Failed to initialize web application.  Exiting.");
                System.exit(1);
            }

            logger.info("Running.");
        } catch (Exception e) {
            logger.error("Error starting application", e);
            applicationContext.close();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        jettyServer();
    }
}
