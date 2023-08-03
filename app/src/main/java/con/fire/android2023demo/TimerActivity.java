package con.fire.android2023demo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {


    private Button button3;
    private int inx = 0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startLoading();
                countdown();
            }
        });
    }


    private void countdown() {
        try {
            countDownTimer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    try {
                        int value = (int) (millisUntilFinished / 1000);
                        Log.d("okhttp", "========" + value);
                        button3.setText("00:" + String.format("%02d", value));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFinish() {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            countDownTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void startLoading() {
        inx = 100;
        new Thread() {
            @Override
            public void run() {
                super.run();

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            button3.setText("" + (inx--));
                        }
                    });
                }
            }
        }.start();
    }
}
