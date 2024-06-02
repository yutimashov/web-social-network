package com.getjavajob.training.timashovy.socialnetwork.web.servlets.image;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static java.util.Objects.isNull;

public class ShowImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream inputStreamImage = ((InputStream) req.getAttribute("inputStreamImage"));
        if (!(isNull(inputStreamImage))) {
            resp.setContentType("image/jpeg");
            try (OutputStream out = resp.getOutputStream()) {
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = inputStreamImage.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }

}
