package con.fire.android2023demo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import con.fire.android2023demo.databinding.ActivityPermissionBinding;

public class PermissionActivity extends AppCompatActivity {
    ActivityPermissionBinding binding;
    String[] permissionArr = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG,};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = requestPermission(PermissionActivity.this, permissionArr, 100);
                if (result) {
                    Toast.makeText(PermissionActivity.this, "判断有权限0", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermissionActivity.this, "判断没有权限3", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean requestPermission(Context context, String[] permissions, int requestCode) {
        boolean result = true;
        List<String> permissionList = new ArrayList<>();
        for (String item : permissions) {
            if (ActivityCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(item);
                result = false;
            }
        }
        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(PermissionActivity.this, permissionList.toArray(new String[permissionList.size()]), requestCode);
        }
        return result;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission(PermissionActivity.this, permissionArr)) {
            Toast.makeText(PermissionActivity.this, "判断有权限2", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PermissionActivity.this, "判断没有权限1", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkPermission(Context context, String[] perm) {
        for (String item : perm) {
            if (ActivityCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
