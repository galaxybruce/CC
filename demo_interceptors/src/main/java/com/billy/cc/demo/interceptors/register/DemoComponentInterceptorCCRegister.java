package com.billy.cc.demo.interceptors.register;

import com.billy.cc.core.component.IComponent;
import com.billy.cc.core.component.IGlobalCCInterceptor;
import com.billy.cc.core.component.register.ICCRegister;
import com.billy.cc.demo.interceptors.LogInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bruce.zhang
 * @date 2023/2/22 16:07
 * @description (亲 ， 我是做什么的)
 * <p>
 * modification history:
 */
public class DemoComponentInterceptorCCRegister implements ICCRegister {

   @Override
   public List<IComponent> getComponents() {
      return null;
   }

   @Override
   public List<IGlobalCCInterceptor> getGlobalCCInterceptors() {
      List<IGlobalCCInterceptor> list = new ArrayList<>();
      list.add(new LogInterceptor());
      return list;
   }
}
