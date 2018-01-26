package com.jd.home.framework.monitor.db.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举工具类,提供缓存枚举数据,在创建枚举对象时写入,根据相应KEY进行读出,加速枚举访问
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class EnumUtils {

    //并发控制HashMap
    private final static Map<Class<?>, Map<String, Object>> STR_CACHE = new ConcurrentHashMap<>(10);
    private final static Map<Class<?>, Map<Integer, Object>> INTEGER_CACHE = new ConcurrentHashMap<>(10);


    public static <T extends Enum<T>> void putIntegerCache(Class<T> c, Integer key, T value){
        Map<Integer, Object> map = INTEGER_CACHE.get(c);
        if(null == map){
            synchronized (INTEGER_CACHE) {
                map = INTEGER_CACHE.get(c);
                if(null == map){
                    map = new ConcurrentHashMap<>();
                    INTEGER_CACHE.put(c, map);
                }
            }
        }
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T getByIntegerCache(Class<T> c, Integer key){
        Map<Integer, Object> map = INTEGER_CACHE.get(c);
        return (T) (map==null?null:map.get(key));
    }



    public static <T extends Enum<T>> void putStrCache(Class<T> c, String key, T value){
        Map<String, Object> map = STR_CACHE.get(c);
        if(null == map){
            synchronized (STR_CACHE) {
                map = STR_CACHE.get(c);
                if(null == map){
                    map = new ConcurrentHashMap<>();
                    STR_CACHE.put(c, map);
                }
            }
        }
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T getByStrCache(Class<T> c, String key){
        Map<String, Object> map = STR_CACHE.get(c);
        return (T) (map==null?null:map.get(key));
    }
}
