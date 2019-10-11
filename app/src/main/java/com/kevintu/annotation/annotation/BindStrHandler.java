package com.kevintu.annotation.annotation;

import android.app.Activity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Create by Kevin-Tu on 2019/10/11.
 */
public class BindStrHandler implements BaseHandler {
    @Override
    public void handleMethodAnnotation(Activity activity, Method method, Annotation annotation) {
    }

    @Override
    public void handleFieldAnnotation(Activity activity, Field field, Annotation annotation) {
        Class fieldType = field.getType();
        if (String.class.isAssignableFrom(fieldType) && annotation instanceof BindStr) {
            int strId = ((BindStr) annotation).value();
            String str = activity.getResources().getString(strId);

            try {
                field.set(activity, str);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
