package com.billy.cc.core.component.register;

import com.billy.cc.core.component.IComponent;

import java.util.List;

/**
 * @author bruce.zhang
 * @date 2023/2/22 15:53
 * @description cc 组件注册
 * 字节码插桩注册方案导致编译速度太慢，这里采用半自动注册，各个模块内手动注册，
 * 使用时通过读取meta-data实例化
 * <p>
 * modification history:
 */
public interface ICCRegister {

   List<IComponent> getComponents();

}
