package con.fire.android2023demo;

import android.app.Application;

import io.branch.referral.Branch;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Branch.enableTestMode();
        Branch.getAutoInstance(this);


    }
}
