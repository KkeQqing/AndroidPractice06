package com.example.androidpractice06;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnOpenNormal;
    private Button btnOpenResult;
    private Button btnOpenCancel;
    private TextView tvResultShow;

    // 【核心】定义一个启动器，用于处理返回结果
    // 当第二页关闭时，这个回调会被触发
    private final ActivityResultLauncher<Intent> secondActivityLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                // 这里处理返回的结果
                if (result.getResultCode() == RESULT_OK) {
                    // 获取返回的数据
                    Intent data = result.getData();
                    if (data != null) {
                        String msg = data.getStringExtra("key_result");
                        tvResultShow.setText("收到成功返回: " + msg);
                        Toast.makeText(this, "成功收到数据", Toast.LENGTH_SHORT).show();
                    }
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    tvResultShow.setText("用户取消了操作");
                    Toast.makeText(this, "操作已取消", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 适配全面屏边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            var systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. 绑定控件
        btnOpenNormal = findViewById(R.id.btn_open_normal);
        btnOpenResult = findViewById(R.id.btn_open_result);
        btnOpenCancel = findViewById(R.id.btn_open_cancel);
        tvResultShow = findViewById(R.id.tv_result_show);

        // 2. 设置按钮点击事件

        // 按钮1：普通打开，不关心返回
        btnOpenNormal.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent); // 普通启动
        });

        // 按钮2：使用新 API 启动，并期待返回结果 (发送参数)
        btnOpenResult.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("key_param", "这是主页传过去的参数"); // 传参
            secondActivityLauncher.launch(intent); // 【关键】用启动器启动
        });

        // 按钮3：同上，但我们在第二页会模拟取消
        btnOpenCancel.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("key_param", "测试取消流程");
            secondActivityLauncher.launch(intent);
        });
    }
}