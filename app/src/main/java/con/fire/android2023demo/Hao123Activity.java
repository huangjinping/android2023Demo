package con.fire.android2023demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivityHao123Binding;
import con.fire.android2023demo.ui.MainActivity;
import con.fire.android2023demo.ui.PermissionActivity;
import con.fire.android2023demo.ui.PermissionNewActivity;
import con.fire.android2023demo.ui.QueriesActivity;
import con.fire.android2023demo.ui.ScreenRecordActivity;
import con.fire.android2023demo.ui.SelectContractActivity;
import con.fire.android2023demo.ui.TimerActivity;
import con.fire.android2023demo.ui.UIFragmentActivity;
import con.fire.android2023demo.ui.ViewActivity;
import con.fire.android2023demo.ui.WebViewActivity;
import con.fire.android2023demo.utils.ToastUtils;

public class Hao123Activity extends AppCompatActivity {
    ActivityHao123Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHao123Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onClickListener();
    }

    private void onClickListener() {
        binding.txtToast01.setOnClickListener(v -> {
            String msg = "以下服务端接口可免 access_token 调用的场景：使用微信云托管通过微信令牌/开放接口服务调用；使用微信云开发通过云函数免服务器发起云调用以下服务端接口可免 access_token 调用的场景：使用微信云托管通过微信令牌/开放接口服务调用；使用微信云开发通过云函数免服务器发起云调用。 ";
//            ToastUtils.showToast(Hao123Activity.this, msg);
            ToastUtils.makeTextOrder(Hao123Activity.this).show();
//            ToastUtils.makeText(Hao123Activity.this, "" + msg, ToastUtils.LENGTH_LONG).show();
//            Toast.makeText(Hao123Activity.this, msg, Toast.LENGTH_SHORT).show();
        });

        binding.txtImage.setText("照片" + Build.VERSION.SDK_INT);
        binding.txtImage.setOnClickListener(v -> startActivity(new Intent(Hao123Activity.this, MainActivity.class)));

        binding.txtWeb.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123Activity.this, WebViewActivity.class);
            startActivity(intent);
        });
        binding.txtPermission.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123Activity.this, PermissionActivity.class);
            startActivity(intent);
        });

        binding.txtSelectContract.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123Activity.this, SelectContractActivity.class);
            startActivity(intent);
        });

        binding.txtQueries.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123Activity.this, QueriesActivity.class);
            startActivity(intent);
        });
        binding.txtTimer.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123Activity.this, TimerActivity.class);
            startActivity(intent);
        });
        binding.txtPop.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123Activity.this, ViewActivity.class);
            startActivity(intent);
        });
        binding.txtUiFragment.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123Activity.this, UIFragmentActivity.class);
            startActivity(intent);
        });
        binding.txtPermissionNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hao123Activity.this, PermissionNewActivity.class);
                startActivity(intent);
            }
        });

        binding.txtScreenRecord.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123Activity.this, ScreenRecordActivity.class);
            startActivity(intent);
        });
    }


}
