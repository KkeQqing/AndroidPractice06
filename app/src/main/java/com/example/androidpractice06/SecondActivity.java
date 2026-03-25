package com.example.androidpractice06;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private Button bt_r, bt_r2, bt_r3;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        // 适配全面屏
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            var systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. 绑定控件
        tvInfo = findViewById(R.id.tv_info);
        bt_r = findViewById(R.id.bt_r);
        bt_r2 = findViewById(R.id.bt_r2);
        bt_r3 = findViewById(R.id.bt_r3);

        // 2. 接收主页传来的参数 (可选)
        String param = getIntent().getStringExtra("key_param");
        if (param != null) {
            tvInfo.setText("收到参数:\n" + param);
        }

        // 3. 设置点击监听
        bt_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 直接关闭，不设置任何结果
                finish();
            }
        });

        bt_r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 准备返回的数据
                Intent resultIntent = new Intent();
                resultIntent.putExtra("key_result", "我是第二个页面返回的成功数据！");

                // 设置结果为 OK，并附带数据
                setResult(Activity.RESULT_OK, resultIntent);

                // 关闭页面
                finish();
            }
        });

        bt_r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置结果为 CANCELED (取消)
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
}