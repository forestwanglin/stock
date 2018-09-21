package ff.three.three.config;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Forest Wang
 * @package io.merculet.config
 * @class JettyConfig
 * @email forest@magicwindow.cn
 * @date 2018/5/23 21:08
 * @description
 */
@Configuration
public class JettyConfig {


    private static final Logger LOGGER = LoggerFactory.getLogger(JettyConfig.class);

    @Bean
    public JettyServletWebServerFactory jettyEmbeddedServletContainerFactory(@Value("${server.port}") final String port,
                                                                             @Value("${jetty.threadPool.maxThreads:200}") final String maxThreads,
                                                                             @Value("${jetty.threadPool.minThreads:8}") final String minThreads,
                                                                             @Value("${jetty.threadPool.idleTimeout:60000}") final String idleTimeout) {
        final JettyServletWebServerFactory factory = new JettyServletWebServerFactory(Integer.valueOf(port));
        factory.addServerCustomizers((JettyServerCustomizer) server -> {
            // Tweak the connection pool used by Jetty to handle incoming HTTP connections
            final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMaxThreads(Integer.valueOf(maxThreads));
            threadPool.setMinThreads(Integer.valueOf(minThreads));
            threadPool.setIdleTimeout(Integer.valueOf(idleTimeout));

//            final ServerConnector serverConnector = server.getBean(ServerConnector.class);
//            String host = null;
//            try {
//                host = InetAddress.getLocalHost().getHostName();
//            } catch (UnknownHostException e) {
//                logger.error("jetty config", e);
//            }
//            serverConnector.setAcceptQueueSize(256);
//            serverConnector.setHost(host);
        });
        return factory;
    }
}