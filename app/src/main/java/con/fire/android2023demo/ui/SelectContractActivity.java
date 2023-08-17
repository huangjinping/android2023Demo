package con.fire.android2023demo.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import con.fire.android2023demo.databinding.ActivitySelectcontractBinding;

//https://blog.csdn.net/s779528166/article/details/79061258
//https://blog.csdn.net/windowsxp2014/article/details/131117214
public class SelectContractActivity extends AppCompatActivity {

    public static int REQUEST_CONTRACT = 1002;
    final String TAG = "SELCS";
    ActivitySelectcontractBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectcontractBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button0.setOnClickListener(v -> startIntent0());
        binding.button1.setOnClickListener(v -> startIntent1());
        binding.button2.setOnClickListener(v -> startIntent2());
        binding.button3.setOnClickListener(v -> startIntent3());
        binding.button4.setOnClickListener(v -> startIntent4());
        binding.button5.setOnClickListener(v -> startIntent5());


    }


    /**
     * 有选项弹框
     */
    private void startIntent0() {

        try {
            Log.d(TAG, "startIntent0");
            resetData();
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, REQUEST_CONTRACT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 没有选项弹框
     */
    private void startIntent1() {

        Log.d(TAG, "startIntent1");
        resetData();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

        startActivityForResult(intent, REQUEST_CONTRACT);
    }

    /**
     * 有默认选项弹框
     */
    private void startIntent2() {
        Log.d(TAG, "startIntent2");
        resetData();
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CONTRACT);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 没有选项弹框
     */
    private void startIntent3() {
        Log.d(TAG, "startIntent3");
        resetData();
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("vnd.android.cursor.dir/phone");

        startActivityForResult(i, REQUEST_CONTRACT);
    }

    private void startIntent4() {
        Log.d(TAG, "startIntent4");
        resetData();
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(i, REQUEST_CONTRACT);
    }

    private void startIntent5() {
        Log.d(TAG, "startIntent5");
        resetData();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, REQUEST_CONTRACT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        onResult0(requestCode, resultCode, data);
//        onResult1(requestCode, resultCode, data);
//        onResult2(requestCode, resultCode, data);
//        onResult3(requestCode, resultCode, data);
//        onResult4(requestCode, resultCode, data);
        onResult5(requestCode, resultCode, data);


    }


    /**
     * 线上
     */
    @SuppressLint("Range")
    private void onResult0(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "requestCode:" + requestCode + "resultCode:" + resultCode);
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

    @SuppressLint("Range")
    private void onResult1(int requestCode, int resultCode, @Nullable Intent data) {
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        Uri contactUri = data.getData();
//
        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
        String contractPhone = null;
        String contactName = null;
        if (cursor != null && cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contractPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            setData(contractPhone, contactName);
        }
    }

    @SuppressLint("Range")
    private void onResult2(int requestCode, int resultCode, @Nullable Intent data) {

        /**
         * 需要权限
         */
        Map<String, String> contactMap = new HashMap<>();
        ContentResolver reContentResolverol = getContentResolver();
        Uri contactData = data.getData();
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(contactData, null, null, null, null);
        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            contactMap.put("name", username);
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);

            while (phone.moveToNext()) {
                String mobile = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (!TextUtils.isEmpty(mobile)) {
                    contactMap.put("mobile", mobile.replace(" ", ""));
//                    contactMap.put("mobile", mobile.replaceAll("[^\\d]+", ""));

                }
            }
        }

        setData(contactMap.get("mobile"), contactMap.get("name"));
    }


    @SuppressLint("Range")
    private void onResult3(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "requestCode:" + requestCode + "resultCode:" + resultCode);
        if (RESULT_OK == resultCode) {
            String contactName = "";
            String contractPhone = "";
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    Cursor cursor = getContentResolver()
                            .query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                                    null, null, null);
                    while (cursor.moveToNext()) {
                        String number = cursor.getString(0);
                        String phoneNum = number.replaceAll("-", "");
                        contractPhone = phoneNum;
                        contactName = cursor.getString(1);

                    }
                    cursor.close();
                }
            }

            setData(contractPhone, contactName);
        }
    }

    @SuppressLint("Range")
    private void onResult4(int requestCode, int resultCode, @Nullable Intent data) {
        /**
         * 需要权限
         */
        Log.d(TAG, "requestCode:" + requestCode + "resultCode:" + resultCode);
        if (RESULT_OK == resultCode) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            //得到ContentResolver对象
            ContentResolver cr = getContentResolver();
            //取得电话本中开始一项的光标
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //取得电话号码
                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
                if (phone != null) {
                    phone.moveToFirst();
                    String phoneNum = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    setData(phoneNum, username);
                }
                phone.close();
                cursor.close();
            }
        }
    }

    @SuppressLint("Range")
    private void onResult5(int requestCode, int resultCode, @Nullable Intent data) {

        Log.d(TAG, "requestCode:" + requestCode + "resultCode:" + resultCode);
        if (RESULT_OK == resultCode) {
            if (data == null) {
                return;
            }
            Cursor query = getContentResolver().query(data.getData(), new String[]{"data1", "display_name"}, null, null, null);
            String str2 = "", str = "";
            if (query != null) {
                if (query.moveToFirst()) {
                    str = query.getString(query.getColumnIndex("data1"));
                    str2 = query.getString(query.getColumnIndex("display_name"));
                } else {
                    str = "";
                }
                query.close();
            } else {
                str = "";
            }
            setData(str, str2);
        }
    }

    private void resetData() {
        setData("", "");
    }

    private void setData(String phone, String name) {
        binding.txtResult.setText(phone + ":" + name);
    }
}