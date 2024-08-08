package con.fire.android2023demo.ui.login;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import con.fire.android2023demo.databinding.ActivityPhoneloginBinding;


/**
 * 电话号码提示
 * https://developers.google.cn/identity/phone-number-hint/android?authuser=3&%3Bhl=zh-cn&hl=zh-cn
 * <p>
 * 获取验证码
 * https://developers.google.com/identity/sms-retriever/user-consent/request?hl=en
 */
public class PhoneLoginActivity extends AppCompatActivity {

    private final int REQUEST_CODE_PHONE_PICKER_GOOGLE = 100;
    private final int REQUEST_CODE_PHONE_PICKER_SIM = 101;

    private final int SMS_CONSENT_REQUEST = 103;
    private final BroadcastReceiver smsVerificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
                switch (smsRetrieverStatus.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                        try {
                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST);
                        } catch (ActivityNotFoundException e) {
                        }
                        break;
                }
            }
        }
    };
    private ActivityPhoneloginBinding binding;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        binding = ActivityPhoneloginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void getCode() {
        SmsRetriever.getClient(this).startSmsUserConsent(null);
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // 34开始必须要设置flag，此5参数的方法只有33才开始支持，因此需要兼容
            registerReceiver(smsVerificationReceiver, intentFilter, SmsRetriever.SEND_PERMISSION, null, Context.RECEIVER_EXPORTED);
        } else {
            registerReceiver(smsVerificationReceiver, intentFilter, SmsRetriever.SEND_PERMISSION, null);
        }

    }

    private void getPhone() {
        boolean hasSim = false;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            hasSim = telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
        } catch (Exception e) {
        }
        if (hasSim) {
            getPhone1();
        } else {
            getPhone2();
        }
    }

    // 读取手机内sim卡的手机号
    private void getPhone1() {
        try {
            Identity.getSignInClient(this).getPhoneNumberHintIntent(GetPhoneNumberHintIntentRequest.builder().build()).addOnSuccessListener(new OnSuccessListener<PendingIntent>() {
                @Override
                public void onSuccess(PendingIntent result) {
                    try {
                        startIntentSenderForResult(new IntentSenderRequest.Builder(result).build().getIntentSender(), REQUEST_CODE_PHONE_PICKER_SIM, null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                        getPhone2(); // 读取sim卡手机号失败时，读取google绑定的手机号
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    getPhone2(); // 读取sim卡手机号失败时，读取google绑定的手机号
                }
            });
        } catch (Exception e) {
            getPhone2(); // 读取sim卡手机号失败时，读取google绑定的手机号
        }
    }

    // 读取谷歌账号绑定的手机号
    private void getPhone2() {
        try {
            HintRequest hintRequest = new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build();
            CredentialsOptions options = new CredentialsOptions.Builder().forceEnableSaveDialog().build();
            PendingIntent intent = Credentials.getClient(this, options).getHintPickerIntent(hintRequest);
            startIntentSenderForResult(intent.getIntentSender(), REQUEST_CODE_PHONE_PICKER_GOOGLE, null, 0, 0, 0);
        } catch (Exception e2) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHONE_PICKER_SIM || requestCode == REQUEST_CODE_PHONE_PICKER_GOOGLE) {
            String phone = "";
            if (resultCode == RESULT_OK && data != null) {
                try {
                    if (requestCode == REQUEST_CODE_PHONE_PICKER_SIM) {
                        phone = Identity.getSignInClient(this).getPhoneNumberFromIntent(data);
                    } else {
                        Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                        if (credential != null && credential.getId() != null) {
                            phone = credential.getId();
                        }
                    }
                    phone = phone.startsWith("+") ? phone.substring(3) : phone;
                } catch (Exception e) {
                }
            }
            if (TextUtils.isEmpty(phone)) {
                // 通用打点-用户取消自动填充手机号
            } else {
                // 通用打点-自动填充手机号成功
                // 填充手机号
            }
        } else if (requestCode == SMS_CONSENT_REQUEST) {
            SmsRetriever.getClient(this).startSmsUserConsent(null);
            if (resultCode == RESULT_OK && data != null) {
                // 获取到的验证码短信全部内容
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                String smsCode = findVerificationCode(message);
                if (smsCode != null) {
                    // do something
                }
            }
        }
//        if (requestCode == CREDENTIAL_PICKER_REQUEST_SIM || requestCode == CREDENTIAL_PICKER_REQUEST_GOOGLE) {
//            String phone = "";
//            if (resultCode == RESULT_OK && data != null) {
//                try {
//                    if (requestCode == CREDENTIAL_PICKER_REQUEST_SIM) {
//                        phone = Identity.getSignInClient(this).getPhoneNumberFromIntent(data);
//                    } else {
//                        Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
//                        if (credential != null && credential.getId() != null) {
//                            phone = credential.getId();
//                        }
//                    }
//                    phone = phone.startsWith("+") ? phone.substring(3) : phone;
//                } catch (Exception e) {
//                }
//            }
//            if (TextUtils.isEmpty(phone)) {
//                // 通用打点-用户取消自动填充手机号
//            } else {
//                // 通用打点-自动填充手机号成功
//                // 填充手机号
//            }
//        } else if (requestCode == SMS_CONSENT_REQUEST) {
//            SmsRetriever.getClient(this).startSmsUserConsent(null);
//            if (resultCode == RESULT_OK && data != null) {
//                // 获取到的验证码短信全部内容
//                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
//                String smsCode = findVerificationCode(message);
//                if (smsCode != null) {
//                    // do something
//                }
//            }
//        }
    }

    // 从短信中读取验证码
    private String findVerificationCode(String message) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String nums = matcher.group();
            if (nums.length() >= 4) {
                return nums;
            }
        }
        return null;
    }
}
