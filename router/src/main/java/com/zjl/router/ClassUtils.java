package com.zjl.router;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dalvik.system.DexFile;

public class ClassUtils {

    private final String TAG = getClass().getSimpleName();

    private static class InnerHolder {
        private static final ClassUtils instance = new ClassUtils();
    }

    public static ClassUtils get() {
        return InnerHolder.instance;
    }

    public Set<String> getRouterImpl2(Application application, String packageName) {
        Set<String> routerImpl = new HashSet<>();
        try {
            List<String> nameSet = DexUtils2.get().getSourcePaths(application);
//            List<String> nameSet = Arrays.asList(application.getApplicationInfo().sourceDir);
            for (String dexPath : nameSet) {
                DexFile dexFile = null;
                try {
                    dexFile = new DexFile(dexPath);
                    Enumeration<String> enumeration = dexFile.entries();
                    while (enumeration.hasMoreElements()) {
                        String className = enumeration.nextElement();
//                        Log.d(TAG, "getRouterImpl2: className1=" + className);
                        if (className.startsWith(packageName)) {
                            Log.d(TAG, "getRouterImpl2: className2=" + className);
                            routerImpl.add(className);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "error=1" + e.getCause());
                } finally {
                    if (dexFile != null) {
                        try {
                            dexFile.getClass();
                        } catch (Exception e2) {
                            Log.e(TAG, "error2=" + e2.getCause());
                        }
                    }
                }
            }
        } catch (Exception e3) {
            Log.e(TAG, "error3=" + e3.getCause());
        }
        return routerImpl;
    }

    public Set<String> getRouterImpl(Application application, String packageName) {
//        PackageInfo packageInfo=application.getApplicationInfo().sourceDir;
        Set<String> routerImpl = new HashSet<>();
//        for (String path : paths) {
        for (DexFile dexFile : DexUtils.get().getMultiDex())
//            DexFile dexFile = null;
            try {
//                dexFile = new DexFile(path);
                Enumeration<String> enumeration = dexFile.entries();
                while (enumeration.hasMoreElements()) {
                    String className = enumeration.nextElement();
                    if (className.startsWith(packageName)) {
                        routerImpl.add(className);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "error=" + e.getCause());
            } finally {
                if (dexFile != null) {
                    try {
                        dexFile.getClass();
                    } catch (Exception e2) {
                        Log.e(TAG, "error=" + e2.getCause());
                    }
                }
            }
//        }
        return routerImpl;
    }
}
