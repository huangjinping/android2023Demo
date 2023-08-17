package con.fire.android2023demo.utils;

import android.util.Log;

import com.google.gson.Gson;

public class LogUtils {

    public static void logS(String TAG, Object object) {
        if (object == null) {
            return;
        }
        if (object instanceof String) {
            Log.d(TAG, "" + object.toString());
        } else {
            Gson gson = new Gson();
            Log.d(TAG, "" + gson.toJson(object));
        }

    }
}
