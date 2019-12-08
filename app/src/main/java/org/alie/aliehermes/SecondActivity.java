package org.alie.aliehermes;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.alie.aliehermes.core.Hermes;
import org.alie.aliehermes.eventabout.EventBus;
import org.alie.aliehermes.eventabout.Student;
import org.alie.aliehermes.service.HermesService;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    private IUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Hermes.getDefault().connect(this, HermesService.class);
        initView();
        initListener();
    }

    private void initView() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
    }

    private void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                doOperate();
                break;
            case R.id.btn2:
                break;
        }
    }

    private void doOperate() {
        //         userManager  进程A的副本
         userManager = Hermes.getDefault().getInstance(IUserManager.class);
        // 调用目标方法
        userManager.getUser();
    }


}
