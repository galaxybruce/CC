package com.billy.android.register

import com.android.build.gradle.AppPlugin
import com.billy.android.register.cc.DefaultRegistryHelper
import com.billy.android.register.cc.ProjectModuleManager
import com.billy.android.register.cc.generator.ManifestGenerator
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 自动注册插件入口
 * @author billy.qi
 * @since 17/3/14 17:35
 */
public class RegisterPlugin implements Plugin<Project> {
    public static final String PLUGIN_NAME = 'cc-register'
    public static final String EXT_NAME = 'ccregister'

    @Override
    public void apply(Project project) {
        println "project(${project.name}) apply ${PLUGIN_NAME} plugin"
        project.extensions.create(EXT_NAME, RegisterExtension)
//        def isApp = ProjectModuleManager.manageModule(project)
//        performBuildTypeCache(project, isApp)
//        if (isApp) {
//            println "project(${project.name}) register ${PLUGIN_NAME} transform"
//            def android = project.extensions.getByType(AppExtension)
//            def transformImpl = new RegisterTransform(project)
//            android.registerTransform(transformImpl)
//            project.afterEvaluate {
//                RegisterExtension config = init(project, transformImpl)//此处要先于transformImpl.transform方法执行
//                if (config.multiProcessEnabled) {
//                    ManifestGenerator.generateManifestFileContent(project, config.excludeProcessNames)
//                }
//            }
//        }

        // 插件只是去掉了字节码插桩自动注册功能，改成半自动注册。如果想改回去，把上面的代码放开，这段代码注释即可
        ////////////////////////////////////////////////////////////////////////////////////////////
        // 半自动注册使用方法：
        //  1. 需要注册的组件的module/build.gradle中添加
        //        manifestPlaceholders = [
        //                "MODULE_NAME"                  : "${project.getName()}"
        //        ]
        //  2. 创建模块内注册类：
        //  如demo中的com.billy.cc.demo.register.DemoCCRegister
        //  3. 在module/AndroidManifest.xml中把第二步创建的类注册到meta-data中
        //  key都是一样的
        //  <meta-data
        //    android:name="com.galaxybruce.component.interface.${MODULE_NAME}"
        //    android:value="com.billy.cc.demo.register.DemoCCRegister"/>
        //
        ////////////////////////////////////////////////////////////////////////////////////////////
        def isApp = project.plugins.hasPlugin(AppPlugin) // 如果项目中使用了galaxybruce-pioneer插件，这里用这样的判断：project.plugins.hasPlugin(AppPlugin)
        if(isApp) {
            project.afterEvaluate {
                RegisterExtension extension = project.extensions.findByName(EXT_NAME) as RegisterExtension
                // 从"合并"后的AndroidManifest.xml中查找进程，并为每个进程创建CC_Provider_进程名称.class，
                // 然后再将provider写入AndroidManifest.xml
                // CC_fork/demo/build/intermediates/merged_manifests/debug/AndroidManifest.xml
                // CC_fork/demo/build/intermediates/javac/debug/classes/com/billy/cc/core/providers/CC_Provider_web.class
                if(extension.multiProcessEnabled) {
                    ManifestGenerator.generateManifestFileContent(project, extension.excludeProcessNames)
                }
            }
        }
    }

    private static void performBuildTypeCache(Project project, boolean isApp) {
        if (!RegisterCache.isSameAsLastBuildType(project, isApp)) {
            RegisterCache.cacheBuildType(project, isApp)
            //兼容gradle3.0以上组件独立运行时出现的问题：https://github.com/luckybilly/CC/issues/62
            //切换app/lib编译时，将transform目录清除
            def cachedJniFile = project.file("build/intermediates/transforms/")
            if (cachedJniFile && cachedJniFile.exists() && cachedJniFile.isDirectory()) {
                FileUtils.deleteDirectory(cachedJniFile)
            }
        }
    }

    static RegisterExtension init(Project project, RegisterTransform transformImpl) {
        RegisterExtension extension = project.extensions.findByName(EXT_NAME) as RegisterExtension
        extension.project = project
        extension.convertConfig()
        DefaultRegistryHelper.addDefaultRegistry(extension.list)
        transformImpl.extension = extension
        return extension
    }

}
