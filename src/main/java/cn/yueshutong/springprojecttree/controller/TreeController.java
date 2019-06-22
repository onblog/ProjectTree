package cn.yueshutong.springprojecttree.controller;

import cn.yueshutong.springprojecttree.db.entity.MethodNode;
import cn.yueshutong.springprojecttree.db.service.MethodNodeService;
import cn.yueshutong.springprojecttree.util.ReadClasspathFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Create by yster@foxmail.com 2019/1/31 0031 19:51
 */
@Controller
public class TreeController {

    @Autowired
    private MethodNodeService methodNodeService;

    /**
     * 返回HTML网页形式的分析结果
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/projecttree",method = RequestMethod.GET)
    public String getViewAll(ModelMap modelMap){
        List<MethodNode> nodes = methodNodeService.findAll();
        if (nodes!=null) {
            nodes = nodes.stream().filter(MethodNode::isFirst).collect(Collectors.toList());
        }
        modelMap.addAttribute("nodes",nodes);
        return "projecttree";
    }

    @RequestMapping(value = "/projecttree/all",method = RequestMethod.GET)
    public String getView(ModelMap modelMap){
        List<MethodNode> nodes = methodNodeService.findAll();
        modelMap.addAttribute("nodes",nodes);
        return "projecttree";
    }

    /**
     * 返回JSON格式的分析结果
     * @return
     */
    @RequestMapping("/projecttree/json")
    @ResponseBody
    public List<MethodNode> getAll(){
        return methodNodeService.findAll();
    }

    @RequestMapping("/projecttree/{methodId}")
    @ResponseBody
    public MethodNode getAll(@PathVariable Long methodId){
        return methodNodeService.findAllById(methodId);
    }

    /**
     * 解决静态资源不加载
     * @param path
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "view/{path}.js",produces = {"application/x-javascript; charset=UTF-8"})
    public String view_js(@PathVariable String path) throws IOException {
        return ReadClasspathFile.read("view/"+path+".js");
    }

    @ResponseBody
    @RequestMapping(value = "view/{path}.css",produces = {"text/css; charset=UTF-8"})
    public String view_html(@PathVariable String path) throws IOException {
        return ReadClasspathFile.read("view/"+path+".css");
    }

}
