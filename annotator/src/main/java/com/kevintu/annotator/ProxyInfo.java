package com.kevintu.annotator;

import com.kevintu.annotation.Constant;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存一个类里所有被注解的元素
 * <p>
 * Create by Kevin-Tu on 2019/10/11.
 */
public class ProxyInfo {

    // 所在包名
    private String packageName;
    // 生成的类名
    private String proxyClassName;
    // 外部类
    private TypeElement typeElement;

    // 保存类里面的所有注解
    //public List<Element> elementList = new ArrayList<>();
    public Map<Integer, VariableElement> injectVariables = new HashMap<>();

    public ProxyInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(classElement);
        String packageName = packageElement.getQualifiedName().toString();
        String className = ClassValidator.getClassName(classElement, packageName);
        this.packageName = packageName;
        this.proxyClassName = className + Constant.SUFFIX;
    }

    public String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import com.kevintu.annotation.*;\n");
        builder.append("import com.kevintu.annotation.R;\n");
        builder.append("import com.kevintu.annotationlibrary.*;\n");
        builder.append("\n");

        builder.append("public class ").append(proxyClassName).append(" implements " + Constant.PROXY + "<" + typeElement.getQualifiedName() + ">");
        builder.append(" {\n");

        generateMethods(builder);
        builder.append("\n");

        builder.append("}\n");
        return builder.toString();
    }

    private void generateMethods(StringBuilder builder) {
        builder.append("@Override\n ");
        builder.append("public void inject(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        for (int id : injectVariables.keySet()) {
            VariableElement element = injectVariables.get(id);
            String name = element.getSimpleName().toString();
            String type = element.asType().toString();
            builder.append("if(source instanceof android.app.Activity) {\n");
            builder.append("host." + name).append(" = ");
            if (id == -1) {
                builder.append("(" + type + ")(((android.app.Activity)source).findViewById(" + "R.id." + name + "));\n");
            } else {
                builder.append("(" + type + ")(((android.app.Activity)source).findViewById(" + id + "));\n");
            }
            builder.append("\n}else{\n");
            builder.append("host." + name).append(" = ");
            if (id == -1) {
                builder.append("(" + type + ")(((android.view.View)source).findViewById( " + "R.id." + name + "));\n");
            } else {
                builder.append("(" + type + ")(((android.view.View)source).findViewById( " + id + "));\n");
            }

            builder.append("\n};");
        }
        builder.append(" }\n");
    }
}
