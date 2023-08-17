package con.fire.android2023demo;

import android.app.Application;
import android.util.Log;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

//https://blog.csdn.net/qq_48656522/article/details/126011280
public class App extends Application implements Thread.UncaughtExceptionHandler {
    @Override
    public void onCreate() {
        super.onCreate();
//        Branch.enableTestMode();
//        Branch.getAutoInstance(this);

//        Thread.setDefaultUncaughtExceptionHandler(this);
//        BugCrash.initStatus(this);


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
