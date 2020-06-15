package com.swufe.bond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.os.Bundle;
import android.widget.Button;

public class SecondFragment2 extends Fragment {
    Button Set,Guanyu;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_second_fragment2, null); // 先解析file.xml布局，得到一个view
        Set = (Button) rootView.findViewById(R.id.btn_setting);
        Guanyu = (Button) rootView.findViewById(R.id.btn_guanyu);
        Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        Guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JieshaoActivity.class));
            }
        });
        return rootView;
    }
}
