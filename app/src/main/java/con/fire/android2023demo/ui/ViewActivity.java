package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.PopupWindowCompat;

import con.fire.android2023demo.databinding.ActivityViewBinding;
import con.fire.android2023demo.widget.DemoPopWindow;

public class ViewActivity extends AppCompatActivity {


    ActivityViewBinding binding;

    private final String TAG = "okht2tp";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.button5.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "" + binding.button5.getWidth());
            }
        });
        showPop();
    }

    private void showPop() {
        DemoPopWindow window = new DemoPopWindow(this);
        PopupWindowCompat.showAsDropDown(window, binding.button5, 0, 0, Gravity.START);

    }


}
