package debug;

import android.app.Application;

import com.billy.cc.core.component.CC;
import com.billy.cc.demo.interceptors.LogInterceptor;

/**
 * @author billy.qi
 * @since 17/11/20 20:02
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);
        CC.registerGlobalInterceptor(new LogInterceptor());
    }
}
