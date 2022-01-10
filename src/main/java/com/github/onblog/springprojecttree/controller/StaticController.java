package com.github.onblog.springprojecttree.controller;

import com.github.onblog.springprojecttree.util.ReadClasspathFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
public class StaticController {

    private Logger logger = LoggerFactory.getLogger(StaticController.class);

    /**
     * 解决静态资源不加载
     */
    @RequestMapping(value = "/tree/**",method = RequestMethod.GET)
    public void view_js(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String uri = request.getRequestURI().trim();
        if (uri.endsWith(".js")){
            response.setContentType("application/javascript");
        }else if (uri.endsWith(".css")){
            response.setContentType("text/css");
        }else if (uri.endsWith(".woff")){
            response.setContentType("application/x-font-woff");
        }else if (uri.endsWith(".ttf")){
            response.setContentType("application/x-font-truetype");
        }else if (uri.endsWith(".html")){
            response.setContentType("text/html");
        }
        byte[] s = ReadClasspathFile.read(uri);
        response.getOutputStream().write(Optional.ofNullable(s).orElse("404".getBytes()));
    }


}
