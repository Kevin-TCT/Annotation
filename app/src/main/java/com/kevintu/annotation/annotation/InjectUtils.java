package com.kevintu.annotation.annotation;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Create by Kevin-Tu on 2019/10/10.
 */
public class InjectUtils {

    /**
     * 运行时通过反射获取注解，并赋值
     * @param activity
     */
    public static void inject(Activity activity) {
        Class cla = activity.getClass(); // 获取activity的字节码对象
        Field[] fields = cla.getDeclaredFields(); // 获取activity对象中的所有字段的引用

        Class viewClass = View.class;
        Class fieldType;

        for (Field field : fields) {
            // 设置字段为可以访问
            // 如果不设置,在获取私有字段的时候,就会抛出异常
            // java.lang.IllegalAccessException: access to field not allowed
            field.setAccessible(true);

            // 判断是否是View或View的子类
            fieldType = field.getType();
            if (!viewClass.isAssignableFrom(fieldType)) {
                continue;
            }

            // 判断是否有相应注解
            boolean haveAnnotation = field.isAnnotationPresent(FindView.class);
            if (!haveAnnotation) {
                continue;
            }

            // 获取相应注解对象
            FindView findView = field.getAnnotation(FindView.class);
            if (findView == null) {
                continue;
            }

            // 获取注解的值
            int id = findView.value();
            // findViewById获取View
            View view = activity.findViewById(id);
            if (view == null) {
                Log.e("kevin","inject() error view id");
            }
            try {
                // 给field赋值
                field.set(activity, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
