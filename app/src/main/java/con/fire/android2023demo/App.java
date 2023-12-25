package con.fire.android2023demo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.lzy.okgo.OkGo;

import me.jessyan.autosize.AutoSizeConfig;

//https://blog.csdn.net/qq_48656522/article/details/126011280
public class App extends Application implements Thread.UncaughtExceptionHandler {


 public static   App  application;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        OkGo.getInstance().init(this);

//        Branch.enableTestMode();
//        Branch.getAutoInstance(this);

//        Thread.setDefaultUncaughtExceptionHandler(this);
//        BugCrash.initStatus(this);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {


        StackTraceElement[] elements = e.getStackTrace();
        StringBuilder reason = new StringBuilder(e.toString());
        if (elements != null && elements.length > 0) {
            for (StackTraceElement element : elements) {
                reason.append("\n");
                reason.append(element.toString());
            }
        }

        Log.d("okhttp", reason.toString());
//        Intent intent = new Intent(this, PostErrorService.class);
//        intent.putExtra("error", reason.toString());
//        startService(intent);
//
        Log.d("okhttp", "=====uncaughtException=======");


//        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(t, e);

    }
}
