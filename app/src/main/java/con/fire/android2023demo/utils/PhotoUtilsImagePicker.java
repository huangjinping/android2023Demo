package con.fire.android2023demo.utils;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import con.fire.android2023demo.FileUtils;


public class PhotoUtilsImagePicker {
    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    private static final String TAG = "PhotoUtils12ImagePicker";
    public AppCompatActivity activity;
    public Callback callback;
    FileUtils fileUtils;
    private File outputImagepath;//存储拍完照后的图片
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap
    private Uri uri = null;

    public PhotoUtilsImagePicker(AppCompatActivity activity, Callback callback) {
        this.activity = activity;
        this.callback = callback;
        this.fileUtils = new FileUtils();
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public void take_Album() {
        Intent pickImageIntent;
        boolean usePhotoPicker = false;
        if (usePhotoPicker && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pickImageIntent =
                    new ActivityResultContracts.PickVisualMedia()
                            .createIntent(
                                    activity,
                                    new PickVisualMediaRequest.Builder()
                                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                                            .build());



        } else {
            pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
            pickImageIntent.setType("image/*");
        }
        activity.startActivityForResult(pickImageIntent, SELECT_PHOTO);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            String path = fileUtils.getPathFromUri(activity, data.getData());

            if (callback != null) {
                callback.getPath(uri, path);
            }
            Log.d(TAG, path);
//            handleImageResult(path, false);
            return;
        }

    }


    public interface Callback {
        void getPath(Uri uri, String path);
    }
}
