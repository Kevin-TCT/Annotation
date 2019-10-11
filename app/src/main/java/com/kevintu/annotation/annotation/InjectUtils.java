package com.kevintu.annotation.annotation;

import android.app.Activity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Create by Kevin-Tu on 2019/10/10.
 */
public class InjectUtils {

    private HashMap<Class, BaseHandler> annotationHandlers;
    private static InjectUtils instance = null;

    private InjectUtils() {
        init();
    }

    private void init() {
        annotationHandlers = new HashMap<>();
        annotationHandlers.put(FindView.class, new FindViewHandler());
        annotationHandlers.put(ViewClick.class, new ViewClickHandler());
        annotationHandlers.put(BindStr.class, new BindStrHandler());
    }

    public static InjectUtils getInstance() {
        if (instance == null) {
            synchronized (InjectUtils.class) {
                if (instance == null) {
                    instance = new InjectUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 运行时通过反射获取注解，并赋值
     *
     * @param activity
     */
    public void inject(Activity activity) {
        injectFields(activity);

        injectMethods(activity);
    }

    private void injectMethods(Activity activity) {
        Class cla = activity.getClass();
        Method[] methods = cla.getDeclaredMethods();

        Set<Map.Entry<Class, BaseHandler>> entries = annotationHandlers.entrySet();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            if (annotations == null || annotations.length == 0) {
                continue;
            }
            for (Annotation annotation : annotations) {
                Iterator<Map.Entry<Class, BaseHandler>> iterable = entries.iterator();
                // TODO: 2019/10/11 双层循环，有待优化 
                while (iterable.hasNext()) {
                    Map.Entry<Class, BaseHandler> entry = iterable.next();
                    if (entry.getKey().isAssignableFrom(annotation.getClass())) {
                        entry.getValue().handleMethodAnnotation(activity, method, annotation);
                    }
                }
            }
        }
    }

    private void injectFields(Activity activity) {
        Class cla = activity.getClass(); // 获取activity的字节码对象
        Field[] fields = cla.getDeclaredFields(); // 获取activity对象中的所有字段的引用

        Set<Map.Entry<Class, BaseHandler>> entries = annotationHandlers.entrySet();
        for (Field field : fields) {
            // 设置字段为可以访问
            // 如果不设置,在获取私有字段的时候,就会抛出异常
            // java.lang.IllegalAccessException: access to field not allowed
            field.setAccessible(true);

            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations == null || annotations.length == 0) {
                continue;
            }
            for (Annotation annotation : annotations) {
                Iterator<Map.Entry<Class, BaseHandler>> iterable = entries.iterator();
                while (iterable.hasNext()) {
                    Map.Entry<Class, BaseHandler> entry = iterable.next();
                    if (entry.getKey().isAssignableFrom(annotation.getClass())) {
                        entry.getValue().handleFieldAnnotation(activity, field, annotation);
                    }
                }
            }
        }
    }
}
