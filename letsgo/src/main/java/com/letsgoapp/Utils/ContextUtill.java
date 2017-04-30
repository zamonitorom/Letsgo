package com.letsgoapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.letsgoapp.Views.MainActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by normalteam on 28.02.17.
 */

public class ContextUtill {

    private static Boolean dataChanged = false;
    @Nullable
    public static Object GetTopContext() {
        Object topActivity = null;
        try {
            Object o = GetApplicationContext();
            topActivity = ((ContextApplication) o).getGlobalVarValue();

        } catch (final ClassNotFoundException | NoSuchMethodException | IllegalArgumentException|InvocationTargetException | IllegalAccessException ignored) {
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

//    public static String GetApplicationPath() {
//        Object o = null;
//        try {
//            o = GetApplicationContext();
//        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        String Path = Environment.getExternalStorageDirectory().getPath() + ((ContextApplication) o).GetPathPakage();
//        return Path;
//    }

//    public static String GetDataBasePath() {
//        return GetApplicationPath() + DB_NAME;
//    }
//
//    public static String GetPhotosPath() {
//        return GetApplicationPath() + PHOTOS;
//    }

    public static void SetTopContext(Activity activity) {
        ((ContextApplication) activity.getApplication()).setGlobalVarValue(activity);
    }

    public static Boolean isDataChanged() {
        return dataChanged;
    }

    public static void setDataChanged(Boolean data) {
        dataChanged = data;
    }

}
