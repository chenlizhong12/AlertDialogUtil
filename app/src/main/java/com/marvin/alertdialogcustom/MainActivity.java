package com.marvin.alertdialogcustom;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDialog_button;
    private final String[] Category = new String[]{  "分享", "编辑", "删除", "取消"};
    private TextView mTv_dialog;
    private Button mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mDialog_button = findViewById(R.id.dialog_button);
        mTv_dialog = findViewById(R.id.tv_dialog);
        mToast = findViewById(R.id.toast);
        mToast.setOnClickListener(this);
        mDialog_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_button:
                AlertDialogUtil.showAlertDialog(this,"更多操作",mTv_dialog,Category);
                break;
            case R.id.toast:
                AlertDialogUtil alertDialogUtil = new AlertDialogUtil();
                alertDialogUtil.showAlertDialogToast(this, "确定删除这条记录");
                alertDialogUtil.setOnDialogListener(new AlertDialogUtil.OnDialogListener() {
                    @Override
                    public void onConfirmClick() {
                        Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
        }
    }
}
