package com.getjavajob.training.timashovy.socialnetwork.web.util;

import static java.lang.String.format;

public final class JspDestinationPath {

    private static final String PATH_FORMAT = "/WEB-INF/jsp/%s.jsp";

    private JspDestinationPath() {
        throw new AssertionError();
    }

    public static String getJspPagePath(String fileName) {
        return format(PATH_FORMAT, fileName);
    }

}
