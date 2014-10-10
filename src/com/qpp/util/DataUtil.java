package com.qpp.util;

import com.qpp.dao.BaseDao;
import com.qpp.model.TUser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by qpp on 8/28/2014.
 */
public class DataUtil extends BaseDao {

    public static void getData(Object model){
        Method[] methods=model.getClass().getDeclaredMethods();
        for (Method method : methods) {
//            method.setAccessible(true);
            Type type = method.getGenericReturnType();
//            String str = type.toString();
            System.out.println("方法返回类型:"+type.toString());
                try {
            if (type.toString().contains("com.qpp.model.")) {
                Object mode = method.invoke(model, null);
                if(mode!=null && mode instanceof Set){
                    for(Iterator it=((Set) mode).iterator();it.hasNext();)
                        getData(it.next());
                }
            }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void main(String[] args) {
        new DataUtil().getData(new TUser());
    }
}
