package con.fire.android2023demo.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import con.fire.android2023demo.databinding.FragmentA306Binding;

public class A306Fragment extends Fragment {


    FragmentA306Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentA306Binding.inflate(inflater);

        binding.button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialog();
            }
        }, 5000);

        return binding.getRoot();
    }

    public void showDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("测试").setMessage("ddddddd").setNegativeButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity(), "dddd", Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com"));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).show();


    }
}
