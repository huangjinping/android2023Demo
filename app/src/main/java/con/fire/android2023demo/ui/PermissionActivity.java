package con.fire.android2023demo.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import con.fire.android2023demo.databinding.ActivityPermissionBinding;

public class PermissionActivity extends AppCompatActivity {
    ActivityPermissionBinding binding;
    String[] permissionArr = {Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG};

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

                    showSetting();
                    Toast.makeText(PermissionActivity.this, "判断有权限0", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermissionActivity.this, "判断没有权限3", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("没有开权限需要去系统权限页面").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toSetting();
            }
        }).show();
    }


    public void toSetting() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
