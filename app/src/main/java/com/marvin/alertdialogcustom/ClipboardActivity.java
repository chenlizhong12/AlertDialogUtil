package com.marvin.alertdialogcustom;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClipboardActivity extends AppCompatActivity {

    private TextView mClip;
    private Button mBt_clip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipboard);
        initView();
        mClip.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardUtil.clicpUtil(ClipboardActivity.this, mClip.getText().toString());
                Toast.makeText(ClipboardActivity.this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ClipboardActivity.this, "被点击了", Toast.LENGTH_SHORT).show();
            }
        });
        mBt_clip.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ClipboardActivity.this, "按钮被点击了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void initView() {
        mClip = findViewById(R.id.tv_clip);
        mBt_clip = findViewById(R.id.bt_clip);
    }
}
