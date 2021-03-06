package com.kevintu.annotationlibrary;

import android.app.Activity;
import android.view.View;
import com.kevintu.annotation.Constant;

/**
 * Create by Kevin-Tu on 2019/10/11.
 */
public class ViewInjector {
    private static final String SUFFIX = "$$ViewInjector";

    public static void injectView(Activity activity) {
        ViewInject proxyActivity = findProxyActivity(activity);
        if (proxyActivity == null) {
            return;
        }
        proxyActivity.inject(activity, activity);
    }

    public static void injectView(Object object, View view) {
        ViewInject proxyActivity = findProxyActivity(object);
        if (proxyActivity == null) {
            return;
        }
        proxyActivity.inject(object, view);
    }

    /**
     * 根据使用注解的类和约定的命名规则，反射获取注解生成的类
     *
     * @param object
     * @return
     */
    private static ViewInject findProxyActivity(Object object) {
        try {
            Class clazz = object.getClass();
            Class injectorClazz = Class.forName(clazz.getName() + Constant.SUFFIX);
            return (ViewInject) injectorClazz.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
