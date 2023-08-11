package con.fire.android2023demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivitySelectcontractBinding;

public class SelectContractActivity extends AppCompatActivity {

    ActivitySelectcontractBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectcontractBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent0();
            }
        });
    }

    public static int REQUEST_CONTRACT = 1002;

    private void startIntent0() {
        try {

            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, REQUEST_CONTRACT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startIntent1() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onResult0(requestCode, resultCode, data);
    }


    @SuppressLint("Range")
    private void onResult0(int requestCode, int resultCode, @Nullable Intent data) {
        if (RESULT_OK == resultCode) {
            Activity activity = this;
            Uri uri = data.getData();
            String contractPhone = null;
            String contactName = null;
            ContentResolver contentResolver = activity.getContentResolver();
            Cursor cursor = null;
            if (uri != null) {
                cursor = contentResolver.query(uri, null, null, null, null);
            }
            while (cursor.moveToNext()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contractPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            cursor.close();
            if (contractPhone != null) {
                contractPhone = contractPhone.replaceAll("-", "");
                contractPhone = contractPhone.replaceAll(" ", "");
            }
            setData(contractPhone, contactName);
        }
    }


    private void setData(String phone, String name) {
        binding.txtResult.setText(phone + ":" + name);
    }
}
