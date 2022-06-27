package com.zjl.router;

import java.lang.reflect.Field;
import java.util.ArrayList;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexFile;

public class DexUtils {

    private DexUtils() {
    }

    private static class InnerHolder {
        private static final DexUtils instance = new DexUtils();
    }

    public static DexUtils get() {
        return InnerHolder.instance;
    }

    public ArrayList<DexFile> getMultiDex() {
        BaseDexClassLoader dexLoader = (BaseDexClassLoader) Thread.currentThread().getContextClassLoader();
        Field f = getField("pathList", getClassByAddressName("dalvik.system.BaseDexClassLoader"));
        Object pathList = getObjectFromField(f, dexLoader);
        Field f2 = getField("dexElements", getClassByAddressName("dalvik.system.DexPathList"));
        Object[] list = getObjectFromField(f2, pathList);
        Field f3 = getField("dexFile", getClassByAddressName("dalvik.system.DexPathList$Element"));
        ArrayList<DexFile> res = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            DexFile d = getObjectFromField(f3, list[i]);
            res.add(d);
        }
        return res;
    }

    private Field getField(String field, Class<?> className) {
        try {
            return className.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<?> getClassByAddressName(String classAddressName) {
        Class mClass = null;
        try {
            mClass = Class.forName(classAddressName);
        } catch (Exception e) {
        }
        return mClass;
    }

    public <T extends Object> T getObjectFromField(Field field, Object arg) {
        try {
            field.setAccessible(true);
            return (T) field.get(arg);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
