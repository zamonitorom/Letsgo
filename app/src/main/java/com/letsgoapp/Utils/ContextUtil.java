package com.letsgoapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ContextUtil {

    @Nullable
    public static Object GetTopContext() {
        Object topActivity = null;
        try {
            Object o = GetApplicationContext();
            topActivity = ((ContextApplication) o).getGlobalVarValue();

        } catch (final ClassNotFoundException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException ignored) {
        }
        return topActivity;
    }

    public static ContextApplication GetContextApplication() {
        if (((Activity) GetTopContext()) != null) {
            return (ContextApplication) ((Activity) GetTopContext()).getApplicationContext();
        }
        return null;
    }

    private static Object GetApplicationContext() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Class<?> activityThreadClass =
                Class.forName("android.app.ActivityThread");
        final Method method = activityThreadClass.getMethod("currentApplication");
        return method.invoke(null, (Object[]) null);
    }

}
