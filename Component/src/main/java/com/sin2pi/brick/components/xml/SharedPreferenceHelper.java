package com.sin2pi.brick.components.xml;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sin2pi.brick.common.utils.StringUtil;
import com.sin2pi.brick.components.xml.exception.PreferenceException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

/**
 * 一个单例，SharedPreferences帮助类。
 * Created by yinhang on 16/2/23.
 */
public class SharedPreferenceHelper {

    private static SharedPreferenceHelper preferenceHelper;
    private Context context;

    private SharedPreferenceHelper(Context context){
        this.context = context.getApplicationContext();
    }

    public static SharedPreferenceHelper instance(Context context){
        if(null == preferenceHelper){
            preferenceHelper = new SharedPreferenceHelper(context);
        }
        return preferenceHelper;
    }

    /**
     * App配置文件,包括各项持久化数据和系统设置
     *
     * @return
     */
    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 编辑默认配置文件
     *
     * @return
     */
    private SharedPreferences.Editor edit() {
        return getPreferences().edit();
    }

    /**
     * 获取名为pref的设置文件
     *
     * @param pref
     * @return
     */
    public SharedPreferences getPreferences(String pref) {
        return context.getSharedPreferences(pref, 0);
    }

    /**
     * 编辑名为pref的设置文件
     *
     * @param pref
     * @return
     */
    public SharedPreferences.Editor edit(String pref) {
        return getPreferences(pref).edit();
    }

    public <T> T load(Class<T> cls) {
        T obj = null;
        try{
            String prefName = StringUtil.camelToUnderline(cls.getSimpleName());
            SharedPreferences preference = getPreferences(prefName);
            obj = cls.getConstructor().newInstance();
            Field[] flds = cls.getDeclaredFields();
            for(Field f : flds){
                Type type = f.getType();
                String name = StringUtil.camelToUnderline(f.getName());
                f.setAccessible(true);
                if(type.equals(int.class) || type.equals(Integer.class)){
                    f.setInt(obj, preference.getInt(name, 0));
                } else if(type.equals(float.class) || type.equals(Float.class)){
                    f.setFloat(obj, preference.getFloat(name, 0f));
                } else if(type.equals(long.class) || type.equals(Long.class)){
                    f.setLong(obj, preference.getLong(name, 0l));
                } else if(type.equals(String.class)){
                    f.set(obj, preference.getString(name, null));
                } else if(type.equals(boolean.class) || type.equals(Boolean.class)){
                    f.setBoolean(obj, preference.getBoolean(name, false));
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException |
                InstantiationException | InvocationTargetException e){
            throw new PreferenceException("Retrieve entity from shared preference failed!", e);
        }
        return obj ;
    }

    public <T> void save(T obj) {
        Class cls = obj.getClass();
        Field[] flds = cls.getDeclaredFields();
        String prefName = StringUtil.camelToUnderline(cls.getSimpleName());
        SharedPreferences.Editor editor = getPreferences(prefName).edit();
        try{
            for(Field f : flds){
                Type type = f.getType();
                String name = StringUtil.camelToUnderline(f.getName());
                f.setAccessible(true);
                if(type.equals(int.class) || type.equals(Integer.class)){
                    editor.putInt(name, f.getInt(obj));
                } else if(type.equals(float.class) || type.equals(Float.class)){
                    editor.putFloat(name, f.getFloat(obj));
                } else if(type.equals(long.class) || type.equals(Long.class)){
                    editor.putLong(name, f.getLong(obj));
                } else if(type.equals(String.class)){
                    editor.putString(name, (String) f.get(obj));
                } else if(type.equals(boolean.class) || type.equals(Boolean.class)){
                    editor.putBoolean(name, f.getBoolean(obj));
                }
            }
            editor.commit();
        } catch (IllegalAccessException e){

        }

    }
}
