package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import con.fire.android2023demo.databinding.ActivityDialogtestBinding;
import con.fire.android2023demo.ui.fragment.DialogFragment1;
import con.fire.android2023demo.utils.LogUtils;

public class DialogTestActivity extends AppCompatActivity {
    ActivityDialogtestBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogtestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LogUtils.logS("111", UUID.randomUUID().toString());
        binding.buttonT0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment1 dialogFragment1 = new DialogFragment1();
                dialogFragment1.show(getSupportFragmentManager(), "22222", 3);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DialogFragment1 dialogFragment1 = new DialogFragment1();
                        dialogFragment1.show(getSupportFragmentManager(), "111", 2);

                    }
                }, 2000);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DialogFragment1 dialogFragment1 = new DialogFragment1();
                        dialogFragment1.show(getSupportFragmentManager(), "333", 1);

                    }
                }, 4000);


            }
        });

        binding.buttonT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
