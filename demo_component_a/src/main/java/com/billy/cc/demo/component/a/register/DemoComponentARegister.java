package com.billy.cc.demo.component.a.register;

import com.billy.cc.core.component.IComponent;
import com.billy.cc.core.component.register.ICCRegister;
import com.billy.cc.demo.component.a.ComponentA;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bruce.zhang
 * @date 2023/2/22 16:07
 * @description (亲 ， 我是做什么的)
 * <p>
 * modification history:
 */
public class DemoComponentARegister implements ICCRegister {

   @Override
   public List<IComponent> getComponents() {
      List<IComponent> list = new ArrayList<>(1);
      list.add(new ComponentA());
      return list;
   }

}
