package con.fire.android2023demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GoogleOpActivity extends AppCompatActivity {


    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googleop);
        button = findViewById(R.id.button);
        Intent intent = getIntent();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext();
            }
        });
    }

    private void onNext() {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.ludo.king&hl=zh&gl=US&inline=true&auao=true&enifd=ANAkzTBcOEiiRA5dIG5xHYWiCHR7Vl15hudPR99QNyp5M69tjJj_fbsLpdWRF-msElvS96_72rcs7wTxltUQ7BJqvLL_-xjlFZQffO_QiKLJ8zWgoRwb_AI15l_wlOrYB9qpSw%3D%3D"));
        intent.setPackage("com.android.vending");
        startActivity(intent);
    }
}
