package con.fire.android2023demo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import con.fire.android2023demo.photo.PhotoCallback;
import con.fire.android2023demo.photo.PhotoSo;
import con.fire.android2023demo.photo.PhotoUtilsImagePicker;
import con.fire.android2023demo.photo.PhotoUtilsSelf;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class MainActivity extends AppCompatActivity {
    String[] permissionArr = {Manifest.permission.CAMERA};
    PhotoSo photoSo;
    String tempMemory = "tempMemorytempMemorytempMemorytempMemorytempMemorytempMemory";
    StringBuilder builder = new StringBuilder();
    private ImageView img_load_take;
    private ImageView img_load_album;
    private ImageView image_target;
    private boolean toLone = true;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    void addchange() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_load_take = findViewById(R.id.img_load_take);
        img_load_album = findViewById(R.id.img_load_album);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            addchange();
        }
        Log.d("img_load", Build.VERSION.RELEASE + "");

        photoSo = new PhotoUtilsSelf(this);
        photoSo.setCallback(new PhotoCallback() {
            @Override
            public void getPath(Uri uri, String path) {
                compress(path);
//                Glide.with(MainActivity.this).load(path).into(image_target);

            }
        });

        img_load_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_target = img_load_take;
                Log.d("okhttps", "====000===11==>>>>");
                ActivityCompat.requestPermissions(MainActivity.this, permissionArr, 101);
            }
        });
        img_load_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_target = img_load_album;
                Log.d("okhttps", "====000===11==>>>>");
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


//    private void compress(String path){
//        Luban.with(this)
//                .load(new File(path))
//                .ignoreBy(100)
////                .setTargetDir(getExternalCacheDir().getAbsolutePath())
//
//                .setCompressListener(new OnCompressListener() {
//                    @Override
//                    public void onStart() {
//                    }
//
//                    @Override
//                    public void onSuccess(int index, File compressFile) {
//                        Log.d("onActivityResult", "0000000000000000005" + compressFile.getAbsolutePath());
//
//                        Glide.with(MainActivity.this).load(compressFile).into(image_target);
//                    }
//
//                    @Override
//                    public void onError(int index, Throwable e) {
//
//                    }
//                }).launch();
//    }


//    private void compress(String path){
//        new Compressor(this)
//                .compressToFileAsFlowable(new File(path))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<File>() {
//                    @Override
//                    public void accept(File file) {
//                        try {
//                            Glide.with(MainActivity.this).load(file).into(image_target);
//                            Log.d("onActivityResult", "0000000000000000005");
//                            Log.d("onActivityResult", "0000000000000000005" + file.getAbsolutePath());
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) {
//                        Log.d("onActivityResult", "0000000000000000006");
//
//                        throwable.printStackTrace();
////                        DisplayToast("压缩失败了");
//                    }
//                });
//    }

//    private void compress(String path) {
//        try {
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//
//                    try {
//                        Bitmap bitmap = Glide.with(MainActivity.this).asBitmap().load(path).submit(1080, 1920).get();
//                        File file = PhotoUtilsSelf.saveBitmap(bitmap, System.currentTimeMillis() + "ko.jpg", MainActivity.this);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d("compress", "============" + file.getAbsolutePath());
//                                Glide.with(MainActivity.this).load(file).into(image_target);
//                            }
//                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }.start();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

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

    private void setTest() {

        try {
            Log.d("compress", "=========0===12");

            for (int i = 0; i < 100; i++) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Log.d("compress", "=========0===" + Thread.currentThread().getName());
                            while (toLone) {

                                builder.append(tempMemory);
                                tempMemory = tempMemory + builder.toString();
                            }
                            Log.d("compress", "=========view===" + builder.length());

                            Log.d("compress", "=========1===" + Thread.currentThread().getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void compress(String path) {
//        setTest();
        Log.d("compress", "======start===0===" + path);
        if ("moto e40".equals(Build.MODEL)) {
            //系统照相机
        }


        new Thread() {
            @Override
            public void run() {
                super.run();

//                try {
//                    Thread.sleep(3000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                Log.d("compress", "=======start===000==");

                Luban.with(MainActivity.this).load(path).ignoreBy(350).setCompressListener(new OnCompressListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        toLone = false;
                        Log.d("compress", "=======end===11==" + file.getAbsolutePath());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(MainActivity.this).load(file).into(image_target);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
            }
        }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (101 == requestCode) {
            Log.d("okhttps", "====000===11==333>>>>");

            if (checkPermission(this, permissions)) {
//                startCamareActivity();
//                photoSo.take_Album();

                if (img_load_album == image_target) {
                    photoSo.take_Album();
                } else if (img_load_take == image_target) {
                    photoSo.take_photo();
                }

//                onwhatApp(this,"13611290917");
            } else {
                Toast.makeText(this, "权限没开！！！！", Toast.LENGTH_SHORT).show();
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
        photoSo.onActivityResult(requestCode, resultCode, data);
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

            Glide.with(MainActivity.this).load(file).into(image_target);

        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}