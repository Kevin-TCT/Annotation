package com.kevintu.annotation.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Create by Kevin-Tu on 2019/10/11.
 */
public class ViewClickHandler implements BaseHandler {

    @Override
    public void handleMethodAnnotation(Activity activity, Method method, Annotation annotation) {
        if (annotation instanceof ViewClick) {
            int id = ((ViewClick)(annotation)).value();
            View view = activity.findViewById(id);
            if (view != null) {
                view.setOnClickListener(v -> {
                    try {
                        method.invoke(activity, v);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public void handleFieldAnnotation(Activity activity, Field field, Annotation annotation) {

    }
}
