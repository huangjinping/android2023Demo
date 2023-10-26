package con.fire.android2023demo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {
    ActivityEditBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
