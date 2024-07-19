package com.getjavajob.training.timashovy.socialnetwork.web.util;

import com.getjavajob.training.timashovy.socialnetwork.web.util.exceptions.WebException;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

public final class WebContextUtils {

    public static ApplicationContext getApplicationContext(ServletContext sc) {
        try {
            return getWebApplicationContext(sc);
        } catch (RuntimeException e) {
            throw new WebException("Failed to get WebApplicationContext", e);
        }
    }

}
