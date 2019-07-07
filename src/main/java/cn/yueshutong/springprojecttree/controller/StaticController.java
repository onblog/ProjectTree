package cn.yueshutong.springprojecttree.controller;

import cn.yueshutong.springprojecttree.util.ReadClasspathFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        }else if (uri.endsWith(".ttf")||uri.endsWith(".woff")){
            response.setContentType("application/octet-stream");
        }else {
            String contentType = new MimetypesFileTypeMap().getContentType(uri);
            response.setContentType(contentType);
        }
        response.getWriter().print(ReadClasspathFile.read(uri));
    }


}
