package con.fire.android2023demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class PostErrorService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String error = intent.getStringExtra("error");
        Log.d("posterror", "---" + error);
        return new Binder();
    }
}
