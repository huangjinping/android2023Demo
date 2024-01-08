package con.fire.android2023demo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivityUploadwebBinding;

public class UploadWebActivity extends AppCompatActivity {

    private ActivityUploadwebBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadwebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
