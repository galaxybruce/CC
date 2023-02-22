package com.billy.cc.core.component.register;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;

/**
 * @author bruce.zhang
 * @date 2023/2/22 16:43
 * @description 读取meta-data中注册的组件
 * <p>
 * modification history:
 */
public class CCRegisterUtil {

   private static final String REGISTER_KEY_PREFIX = "com.galaxybruce.component.interface.";

   public static @NonNull List<ICCRegister> getRegisterInfo(@NonNull Context context) {
      List<ICCRegister> list = new ArrayList<>();
      try {
         ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                 context.getPackageName(), PackageManager.GET_META_DATA);
         Set<String> keySet = appInfo.metaData.keySet();
         if(keySet == null || keySet.isEmpty()) {
            return list;
         }
         for (String key : keySet) {
            if(key.startsWith(REGISTER_KEY_PREFIX)) {
               String className = appInfo.metaData.getString(key);
               if (!TextUtils.isEmpty(className)) {
                  try {
                     Class<?> clazz = Class.forName(className);
                     Object obj = clazz.getConstructor().newInstance();
                     if (obj instanceof ICCRegister) {
                        list.add((ICCRegister) obj);
                     }
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
               }
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return list;
   }
}
