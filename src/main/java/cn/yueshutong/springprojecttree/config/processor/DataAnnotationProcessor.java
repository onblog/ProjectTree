package cn.yueshutong.springprojecttree.config.processor;

import cn.yueshutong.springprojecttree.config.annotation.EnableProjectTree;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.*;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Create by yster@foxmail.com 2018/11/4 0004 15:36
 */
public class DataAnnotationProcessor extends AbstractProcessor {
    private Messager messager; //用于打印日志
    private Elements elementUtils; //用于处理元素
    private Filer filer;  //用来创建java文件或者class文件

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes(){
        Set<String> set = new HashSet<>();
        set.add(EnableProjectTree.class.getCanonicalName());
        return Collections.unmodifiableSet(set);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE,"-----Proejct Tree Start");
        try {
            // 返回被注释的节点
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(EnableProjectTree.class);
            //存放注释value
            List<String> info = new ArrayList<>();
            for (Element e : elements) {
                // 如果注释在类上，获取注释信息
                if (e.getKind() == ElementKind.CLASS && e instanceof TypeElement) {
                    TypeElement t = (TypeElement) e;
                    String qname = t.getQualifiedName().toString();
                    EnableProjectTree annotation = t.getAnnotation(EnableProjectTree.class);
                    info.add(annotation.value());
                    info.add(getPackage(qname));
                    messager.printMessage(Diagnostic.Kind.NOTE,getPackage(qname));
                    break;
                }
            }
            // 未在类上使用注释则直接返回，返回false停止编译
            if (info.isEmpty()) {
                return true;
            }
            // 生成一个Java源文件
            JavaFileObject sourceFile = filer.createSourceFile("ProjectTreeAspect");
            // 写入代码
            createSourceFile(info,sourceFile.openWriter());
            // 手动编译
            compile(sourceFile.toUri().getPath());
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR,e.getMessage());
        }
        messager.printMessage(Diagnostic.Kind.NOTE,"-----Proejct Tree End");
        return true;
    }

    private void createSourceFile(List<String> set, Writer writer) throws IOException {
        writer.write("package "+set.get(1)+";\n" +
                "import org.springframework.boot.autoconfigure.domain.EntityScan;\n" +
                "import org.springframework.context.annotation.ComponentScan;\n" +
                "import org.springframework.data.jpa.repository.config.EnableJpaRepositories;\n"+
                "import cn.yueshutong.springprojecttree.core.around.AroundMethod;\n" +
                "import org.aspectj.lang.ProceedingJoinPoint;\n" +
                "import org.aspectj.lang.annotation.Around;\n" +
                "import org.aspectj.lang.annotation.Aspect;\n" +
                "import org.aspectj.lang.annotation.Pointcut;\n" +
                "import org.springframework.stereotype.Component;\n" +
                "\n" +
                "import java.util.Random;\n" +
                "\n" +
                "@ComponentScan(basePackages = \"cn.yueshutong.springprojecttree\")\n" +
                "@EntityScan(basePackages = \"cn.yueshutong.springprojecttree.database.entity\")\n" +
                "@EnableJpaRepositories(basePackages = \"cn.yueshutong.springprojecttree.database.dao\")\n"+
                "@Component\n" +
                "@Aspect\n" +
                "public class ProjectTreeAspect {\n" +
                "\n" +
                "    //声明切点\n" +
                "    @Pointcut(\""+set.get(0)+"\")\n" +
                "    public void pointcut(){}\n" +
                "\n" +
                "    @Around(\"pointcut()\")\n" +
                "    public Object around(ProceedingJoinPoint pjp){\n" +
                "        int identify = new Random().nextInt();\n" +
                "        AroundMethod.playBeforeMethod(pjp,identify);\n" +
                "        Object obj = null;\n" +
                "        try {\n" +
                "            obj = pjp.proceed();\n" +
                "        } catch (Throwable throwable) {\n" +
                "            throwable.printStackTrace();\n" +
                "        }\n" +
                "        AroundMethod.playAfterMethod(pjp,identify,obj);\n" +
                "        return obj;\n" +
                "    }\n" +
                "\n" +
                "}");
        writer.flush();
        writer.close();
    }

    /**
     * 编译文件
     * @param path
     * @throws IOException
     */
    private void compile(String path) throws IOException {
        //拿到编译器
        JavaCompiler complier = ToolProvider.getSystemJavaCompiler();
        //文件管理者
        StandardJavaFileManager fileMgr =
                complier.getStandardFileManager(null, null, null);
        //获取文件
        Iterable units = fileMgr.getJavaFileObjects(path);
        //编译任务
        JavaCompiler.CompilationTask t = complier.getTask(null, fileMgr, null, null, null, units);
        //进行编译
        t.call();
        fileMgr.close();
    }

    /**
     * 读取类名
     * @param name
     * @return
     */
    private String getClassName(String name) {
        String result = name;
        if (name.contains(".")) {
            result = name.substring(name.lastIndexOf(".") + 1);
        }
        return result;
    }

    /**
     * 读取包名
     * @param name
     * @return
     */
    private String getPackage(String name) {
        String result = name;
        if (name.contains(".")) {
            result = name.substring(0, name.lastIndexOf("."));
        }else {
            result = "";
        }
        return result;
    }

}
