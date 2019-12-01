package org.alie.aliehermes;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.alie.aliehermes.eventabout.EventBus;
import org.alie.aliehermes.eventabout.Student;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        initListener();
    }

    private void initView() {
        btn1 = findViewById(R.id.btn1);
    }

    private void initListener() {
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                doSendEventBus();
                break;
        }
    }

    private void doSendEventBus() {
        EventBus.getDefault().post(new Student("西瓜", 12));
    }


}
