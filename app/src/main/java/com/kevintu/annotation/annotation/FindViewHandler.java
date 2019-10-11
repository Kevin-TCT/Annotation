package com.kevintu.annotation.annotation;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Create by Kevin-Tu on 2019/10/11.
 */
public class FindViewHandler implements BaseHandler {

    @Override
    public void handleMethodAnnotation(Activity activity, Method method, Annotation annotation) {
    }

    @Override
    public void handleFieldAnnotation(Activity activity, Field field, Annotation annotation) {
        // 判断是否是View或View的子类
        Class fieldType = field.getType();
        if (View.class.isAssignableFrom(fieldType) && annotation instanceof FindView) {
            // 获取注解的值
            int id = ((FindView) annotation).value();
            // findViewById获取View
            View view = activity.findViewById(id);
            if (view == null) {
                Log.e("kevin", "inject() error view id");
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
