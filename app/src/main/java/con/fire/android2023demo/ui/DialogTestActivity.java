package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import con.fire.android2023demo.databinding.ActivityDialogtestBinding;
import con.fire.android2023demo.ui.fragment.CommonChooseDialogFragment;
import con.fire.android2023demo.ui.fragment.DialogFragment1;
import con.fire.android2023demo.ui.fragment.RepayComplaintDialogFragment;
import con.fire.android2023demo.utils.LoadingDialogUtils;
import con.fire.android2023demo.utils.LogUtils;

public class DialogTestActivity extends AppCompatActivity {
    protected CommonChooseDialogFragment networkSettingDialogFragment;
    ActivityDialogtestBinding binding;
    LoadingDialogUtils dialogUtils = new LoadingDialogUtils();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogtestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LogUtils.logS("111", UUID.randomUUID().toString());
//        networkSettingDialogFragment = CommonChooseDialogFragment.newInstance();
//        networkSettingDialogFragment = null;

        binding.buttonT0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                creatTestOOOFm();
            }
        });

        binding.buttonT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openzIndexV2();
            }
        });
        binding.btn195.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show195();
            }
        });

        binding.btn154.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show154();
            }
        });
    }


    private void show195() {
//        this.finish();
        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {

                dialogUtils.showLoadingDialog(DialogTestActivity.this, "ddddd");
            }
        }, 3000);
    }

    public void creatTestOOOFm() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    try {
                        String mise = "creatTestOOOFm";
                        List data = new ArrayList();

                        Log.d("okhttp", "==========" + System.currentTimeMillis());
                        while (true) {
                            /**
                             * json采集
                             */
                            mise += mise + "cllllllllllllllllll";
                            data.add("cllllllllllllllllll" + mise + System.currentTimeMillis() + "cc");
                            Gson gson = new Gson();
                            Log.d("okhttp", gson.toJson(data));
                        }
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }


    private void openzIndexV2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                showNetworkSettingDialogFragment();
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                showNetworkSettingDialogFragment();
            }
        }, 4000);
    }

    public void showNetworkSettingDialogFragment() {
        try {
            if (networkSettingDialogFragment != null) {

                networkSettingDialogFragment.show(getSupportFragmentManager(), getClass().getName() + "NetworkSettingdialogfragment");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//
//    public void showNetworkSettingDialogFragment() {
//        try {
//            dismissNetworkSettingDialogFragment();
//            if (networkSettingDialogFragment == null) {
//                networkSettingDialogFragment = CommonChooseDialogFragment.newInstance();
//                networkSettingDialogFragment.show(getSupportFragmentManager(), getClass().getName() + "NetworkSettingdialogfragment");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void dismissNetworkSettingDialogFragment() {
//        try {
//            if (networkSettingDialogFragment != null) {
//                networkSettingDialogFragment.dismiss();
//                networkSettingDialogFragment = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private void openzIndex() {
        DialogFragment1 dialogFragment1 = new DialogFragment1();
        dialogFragment1.show(getSupportFragmentManager(), "22222", 3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogFragment1 dialogFragment1 = new DialogFragment1();
                dialogFragment1.show(getSupportFragmentManager(), "111", 2);

            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogFragment1 dialogFragment1 = new DialogFragment1();
                dialogFragment1.show(getSupportFragmentManager(), "333", 1);

            }
        }, 4000);
    }


    private void show154() {
        RepayComplaintDialogFragment dialogFragment1 = RepayComplaintDialogFragment.newInstance("2");
        dialogFragment1.show(getSupportFragmentManager(), "3333");
        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                RepayComplaintDialogFragment dialogFragment1 = RepayComplaintDialogFragment.newInstance("2");
                dialogFragment1.show(getSupportFragmentManager(), "3333");
            }
        }, 3000);
    }

}
