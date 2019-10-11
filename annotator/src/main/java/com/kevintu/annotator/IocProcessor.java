package com.kevintu.annotator;

import com.google.auto.service.AutoService;
import com.kevintu.annotation.BindViewById;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

// TODO: 2019/10/11 Processor注册方式
// 1、通过第三方库自动注册：
//      1、implementation 'com.google.auto.service:auto-service:1.0-rc6'
//      2、@AutoService(Processor.class)
// 2、手动注册processor：
//      1、新建一个resources/目录，与java/目录同级，在里边建一个javax.annotation.processing.Processor文件
//      2、在该文件里写上MyProcessor的路径，com.kevintu.annotator.IocProcessor
@AutoService(Processor.class)
public class IocProcessor extends AbstractProcessor {

    /**
     * 生成代码用的
     */
    private Filer mFileUtils;

    /**
     * 跟元素相关的辅助类，帮助我们去获取一些元素相关的信息
     * - VariableElement  一般代表成员变量
     * - ExecutableElement  一般代表类中的方法
     * - TypeElement  一般代表类
     * - PackageElement  一般代表Package
     */
    private Elements mElementUtils;

    /**
     * 跟日志相关的辅助类
     */
    private Messager mMessager;

    // 保存所有ProxyInfo
    private Map<String, ProxyInfo> mProxyInfo = new HashMap<>();


    /**
     * 初始化
     *
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFileUtils = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    /**
     * 添加自己需要支持的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(BindViewById.class.getCanonicalName());
        return annotationTypes;
    }

    /**
     * 设置支持的版本(固定写法)
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 开始处理注解
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mProxyInfo.clear();

        // 获取被注解的元素
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindViewById.class);
        for (Element element : elements) {
            // 检查Element类型
            if (!checkAnnotationValid(element)) {
                return false;
            }
            // 获取这个成员变量
            VariableElement variableElement = (VariableElement) element;
            // 获取这个成员变量的外部类
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            // 获取外部类的类名
            String qualifiedName = typeElement.getQualifiedName().toString();
            // 一个类的注解都在一个ProxyInfo中处理
            ProxyInfo proxyInfo = mProxyInfo.get(qualifiedName);
            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo(mElementUtils, typeElement);
                mProxyInfo.put(qualifiedName, proxyInfo);
            }
            // 获取该变量的相应注解
            BindViewById annotation = variableElement.getAnnotation(BindViewById.class);
            int id = annotation.value();
            proxyInfo.injectVariables.put(id, variableElement);
        }

        // 生成类
        for (String key : mProxyInfo.keySet()) {
            ProxyInfo proxyInfo = mProxyInfo.get(key);
            try {
                // 创建一个新的源文件，并返回一个对象以允许写入它
                JavaFileObject javaFileObject = mFileUtils.createSourceFile(proxyInfo.getProxyClassFullName(), proxyInfo.getTypeElement());
                Writer writer = javaFileObject.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                error(proxyInfo.getTypeElement(), "Unable to write injector for type %s: %s", proxyInfo.getTypeElement(), e.getMessage());
            }
        }

        return true;
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return super.getCompletions(element, annotation, member, userText);
    }

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    /**
     * 检测注解使用是否正确
     *
     * @param element
     * @return
     */
    private boolean checkAnnotationValid(Element element) {
        // 检测这个变量是不是私有的
        if (element.getModifiers().contains(Modifier.PRIVATE)) {
            error(element, "%s() must can not be private.", element.getSimpleName());
            return false;
        }
        return true;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mMessager.printMessage(Diagnostic.Kind.ERROR, message);
    }
}
