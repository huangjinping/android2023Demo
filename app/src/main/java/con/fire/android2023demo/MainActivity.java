package con.fire.android2023demo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import con.fire.android2023demo.utils.PhotoUtils;

public class MainActivity extends AppCompatActivity {
    String[] permissionArr = {Manifest.permission.CAMERA,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };
    PhotoUtils photoUtils;
    private ImageView img_load;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    public void onwhatApp(Context context, String phone) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("https://web.whatsapp.com/");
        intent.setData(content_url);
        startActivity(intent);
//        try {
//            try {
//                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + phone + "&text=" + ""));
//                intent.setPackage("com.whatsapp");
//                context.startActivity(intent);
//
//            } catch (Exception e) {
//
//
//                e.printStackTrace();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_load = findViewById(R.id.img_load);

        photoUtils = new PhotoUtils(this, new PhotoUtils.Callback() {
            @Override
            public void getPath(Uri uri, String path) {

                Log.d("getPath", "======" + path);
                compress(path);
            }
        });


        img_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("okhttps", "====000===11==>>>>");
//                BranchEvent be = new BranchEvent("dd1d");
//                be.logEvent(MainActivity.this);

//                FileUtils.saveToSDCard(MainActivity.this,"siiw.txt","ddd");
//                String fileContent = FileUtils.getFileContent(MainActivity.this, "siiw.txt");
//                Log.d("okhttps", "www"+fileContent);


                ActivityCompat.requestPermissions(MainActivity.this, permissionArr, 101);
            }
        });

        try {
            createLockTerm();

//            createLockApplyAmount();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createLockTerm() throws JSONException {

        Double maxDuration = 14d;

        String[] lockDuration = "+7,+7,*2".split(",");
        JSONArray termList = new JSONArray();
        for (int i = 0; i < lockDuration.length; i++) {
            JSONObject item = new JSONObject();
            if (lockDuration[i].startsWith("*")) {
                maxDuration *= Double.parseDouble(lockDuration[i].replaceAll("[^\\d.]", ""));
            } else {
                maxDuration += Double.parseDouble(lockDuration[i].replaceAll("[^\\d.]", ""));
            }
            item.put("lockTerm", maxDuration);
            termList.put(item);
        }
        Log.d("createLockTerm", termList.toString());
    }

    private void createLockApplyAmount() throws JSONException {
        Double maxCreditAmount = 2000d;
        String[] lockDuration = "+1000,+1000,*2".split(",");
        JSONArray lockAmountList = new JSONArray();
        for (int i = 0; i < lockDuration.length; i++) {
            JSONObject item = new JSONObject();
            if (lockDuration[i].startsWith("*")) {
                maxCreditAmount *= Double.parseDouble(lockDuration[i].replaceAll("[^\\d.]", ""));
            } else {
                maxCreditAmount += Double.parseDouble(lockDuration[i].replaceAll("[^\\d.]", ""));
            }
            item.put("LockAmount", maxCreditAmount);
            lockAmountList.put(item);
        }
        Log.d("lockAmountList", lockAmountList.toString());
    }


    private void compress(String path) {

        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    try {
                        Bitmap bitmap = Glide.with(MainActivity.this).asBitmap().load(path).submit(1080, 1920).get();
                        File file = PhotoUtils.saveBitmap(bitmap, System.currentTimeMillis() + "ko.jpg", MainActivity.this);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("compress", "============" + file.getAbsolutePath());
                                Glide.with(MainActivity.this).load(file).into(img_load);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();


        } catch (Exception e) {
            e.printStackTrace();
        }


//        Luban.with(this)
//                .load(new File(path))
//                .ignoreBy(100)
////                .setTargetDir(getExternalCacheDir().getAbsolutePath())
//
//                .setCompressListener(new OnCompressListener() {
//
//                    @Override
//                    public void onStart() {
//                        Log.d("getPath", "======11");
//
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//                        Glide.with(MainActivity.this).load(file).into(img_load);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("getPath", "======" + e.getMessage());
//
//                    }
//                }).launch();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (101 == requestCode) {
            Log.d("okhttps", "====000===11==333>>>>");

            if (checkPermission(this, permissions)) {
//                startCamareActivity();
//                photoUtils.take_Album();
                photoUtils.take_photo();
//                onwhatApp(this,"13611290917");
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        photoUtils.onActivityResult(requestCode, resultCode, data);
        Log.d("okhttps", "==onActivityResult==>onActivityResult>>>");
//        setCompress(mImageUri.getPath(), CALLBACK_TYPE_CODE);
    }

    /*** 压缩图片*/
    public void setCompress(String path, int requestCode) {

        Log.d("okhttps", "=" + path);

        int maxWidth = 1000, maxHeight = 1000;//定义目标图片的最大宽高，若原图高于这个数值，直接赋值为以上的数值
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        int originWidth = bitmap.getWidth();
        int originHeight = bitmap.getHeight();
        if (originWidth < maxWidth && originHeight < maxHeight) {
            return;
        }
        int width = originWidth;
        int height = originHeight;

        if (originWidth > maxWidth) {
            width = maxWidth;
            double i = originWidth * 1.0 / maxWidth;
            height = (int) Math.floor(originHeight / i);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

        }
        if (height > maxHeight) {
            height = maxHeight;
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        }
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            Glide.with(MainActivity.this).load(file).into(img_load);

        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}