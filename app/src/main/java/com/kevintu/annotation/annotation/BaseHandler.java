package com.kevintu.annotation.annotation;

import android.app.Activity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Create by Kevin-Tu on 2019/10/11.
 */
public interface BaseHandler {

    void handleMethodAnnotation(Activity activity, Method method, Annotation annotation);

    void handleFieldAnnotation(Activity activity, Field field, Annotation annotation);
}
