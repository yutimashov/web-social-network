package com.getjavajob.training.timashovy.socialnetwork.web.config;

import com.getjavajob.training.timashovy.socialnetwork.dao.config.PersistenceConfig;
import org.springframework.lang.NonNull;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class AnnotationConfigWebApplicationInitializer implements WebApplicationInitializer {

    private static final int SESSION_LIFETIME = 10 * 60;

    @Override
    public void onStartup(@NonNull ServletContext servletContext) {
        servletContext.addListener(new SessionListener());
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.setServletContext(servletContext);
        ctx.register(ApplicationConfig.class, PersistenceConfig.class);
        ctx.refresh();
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(ctx));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    private static class SessionListener implements HttpSessionListener {

        @Override
        public void sessionCreated(HttpSessionEvent event) {
            event.getSession().setMaxInactiveInterval(SESSION_LIFETIME);
        }

    }

}
