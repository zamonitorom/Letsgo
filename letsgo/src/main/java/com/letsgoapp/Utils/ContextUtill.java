package com.letsgoapp.Utils;

import android.app.Activity;
import android.os.Environment;
import android.support.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by normalteam on 28.02.17.
 */

public class ContextUtill {


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

}
