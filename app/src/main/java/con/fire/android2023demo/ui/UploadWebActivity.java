package con.fire.android2023demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import con.fire.android2023demo.FileUtils;
import con.fire.android2023demo.databinding.ActivityUploadwebBinding;
import con.fire.android2023demo.utils.ExifDataCopier;
import con.fire.android2023demo.utils.ImageResizer;

/**
 * https://www.jianshu.com/p/444932cf5d41
 * https://www.cnblogs.com/cw828/p/10110973.html
 */
public class UploadWebActivity extends AppCompatActivity {

    public static final int SELECT_PHOTO = 2;//启动相册标识
    WebView webView;
    ValueCallback mUploadCallBack;
    ValueCallback<Uri[]> mUploadCallBackAboveL;
    FileUtils fileUtils;
    private ActivityUploadwebBinding binding;
    private ImageResizer imageResizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadwebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webView = binding.webView;
        webView = new WebView(this);

        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        decorView.addView(webView, 1, 1);


        webView.setWebViewClient(new MyWebViewClient());
        this.fileUtils = new FileUtils();
        this.imageResizer = new ImageResizer(this, new ExifDataCopier());

        webView.setWebChromeClient(new MyWebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);//是否支持 Local Storage
        settings.setJavaScriptEnabled(true);//支持JavaScript
        settings.setUseWideViewPort(true);//使Webview具有普通的视口(例如普通的桌面浏览器)
        settings.setLoadWithOverviewMode(true);//加载完全缩小的WebView
        settings.setSupportZoom(true);// 设置可以支持缩放
        settings.setBuiltInZoomControls(true);// 设置启用缩放功能
        settings.setDisplayZoomControls(true);//显示缩放控制按钮
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);


//        -----------------------------___>>>>>>>>>>>>>>>>>1>>>>>>>
        settings.setDatabaseEnabled(true);
        settings.setTextZoom(100);
        //不显示滚动条
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setVerticalScrollBarEnabled(false);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webView.loadUrl("http://10.1.2.95:8092/inxupload.html?v=" + System.currentTimeMillis());
//        webView.loadUrl("https://www.baidu.com");

        binding.btnSubmit.setOnClickListener(view -> {
            String result = System.currentTimeMillis() + "";
            webView.loadUrl("javascript:onVSLogEvent('" + result + "')");
        });
    }

    private void showFileChooser() {

        Toast.makeText(this, "打开相册" + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
        Intent pickImageIntent;
        boolean usePhotoPicker = false;
        pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickImageIntent.setType("image/*");
        startActivityForResult(pickImageIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String compressPath = fileUtils.getPathFromUri(this, data.getData());
                    compressPath = imageResizer.resizeImageIfNeeded(compressPath, 1080d, 1920d, 90);

                    if (!fileUtils.isPicture(new File(compressPath))) {
                        return;
                    }
                    Uri newUri = null;
                    newUri = Uri.fromFile(new File(compressPath));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (mUploadCallBackAboveL != null) {
                            if (newUri != null) {
                                mUploadCallBackAboveL.onReceiveValue(new Uri[]{newUri});
                                mUploadCallBackAboveL = null;
                                return;
                            }

                        }
                    } else if (mUploadCallBack != null) {
                        if (newUri != null) {
                            mUploadCallBack.onReceiveValue(newUri);
                            mUploadCallBack = null;
                            return;
                        }
                    }
                } else {
                    clearUploadMessage();
                }
                break;
        }

    }

    private void clearUploadMessage() {
        if (mUploadCallBackAboveL != null) {
            mUploadCallBackAboveL.onReceiveValue(null);
            mUploadCallBackAboveL = null;
        }
        if (mUploadCallBack != null) {
            mUploadCallBack.onReceiveValue(null);
            mUploadCallBack = null;
        }
    }


    private class MyWebViewClient extends WebViewClient {

//        在api 24（7.0）以下的版本的时候，只会回调shouldOverrideUrlLoading(WebView view, String url)方法
//        在api 24及以上版本的时候，只会回调shouldOverrideUrlLoading（WebView view, WebResourceRequest request)方法
//        注：方法中return true 进行url拦截自己处理，return false由webview系统自己处理。


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            try {


                if (!url.startsWith("http") && !url.startsWith("http")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d("onReceivedError", "" + error.getErrorCode() + "======" + error.getDescription());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            }
        }


        //        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            //            //这里需要为true，否则网页中出现非http，https协议的方式会打不开，例如，https://www.jianshu.com/p/4860097148c0
//            return super.shouldOverrideUrlLoading(view, request);
//        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            try {
                if (url.contains("static/Mpsuccess.html")) {
                    //跳转页面
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //        onReceivedSslError  此方法最好不要使用。
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);

            handler.proceed();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {


        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            mUploadCallBack = valueCallback;
            showFileChooser();
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            mUploadCallBack = valueCallback;
            showFileChooser();
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            mUploadCallBack = valueCallback;
            showFileChooser();
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            mUploadCallBackAboveL = filePathCallback;
            showFileChooser();
            return true;
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (TextUtils.isEmpty(title)) {
                //赋值标题赋值操作
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }
}
