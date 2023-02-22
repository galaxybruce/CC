package com.billy.cc.demo.component.kt.register

import com.billy.cc.core.component.register.ICCRegister
import com.billy.cc.core.component.IComponent
import com.billy.cc.demo.component.kt.KtComponent
import com.billy.cc.core.component.IGlobalCCInterceptor
import java.util.ArrayList

/**
 * @author bruce.zhang
 * @date 2023/2/22 16:07
 * @description (亲 ， 我是做什么的)
 *
 *
 * modification history:
 */
class DemoComponentKtCCRegister : ICCRegister {

    override fun getComponents(): List<IComponent> {
        val list: MutableList<IComponent> = ArrayList()
        list.add(KtComponent())
        return list
    }

    override fun getGlobalCCInterceptors(): List<IGlobalCCInterceptor>? {
        return null
    }
}