package com.marvin.alertdialogcustom;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ┏┓　   ┏┓
 * ┏┛┻━━━━━┛┻━┓
 * ┃　　　　   ┃
 * ┃　━　━　   ┃
 * ████━████   ┃
 * ┃　　　　   ┃
 * ┃　 ┻　    ┃
 * ┗━┓      ┏━┛
 * 　┃      ┃
 * 　┃ 0BUG ┗━━━┓
 * 　┃0Error     ┣┓
 * 　┃0Warning   ┏┛
 * 　┗┓┓┏━┳┓┏┛ ━
 * 　　┃┫┫ ┃┫┫
 * 　　┗┻┛ ┗┻┛
 * Created by clz on 2019/7/19
 */
public class AlertDialogUtil {
    private  OnDialogListener mOnDialogListener;

    public static void showAlertDialog(Context context, String title, final TextView tv, final String[] args) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_view, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title_alert_dialog_addbook);
        ListView list = (ListView) view.findViewById(R.id.lv_alert_dialog_addbook);
        tvTitle.setText(title);
        ListAdapter adpter = new ArrayAdapter<String>(context, R.layout.item_dialog_view, R.id.tv_item_listview_categroy, args);
        list.setAdapter(adpter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(args[position]);
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
    }

    public  void showAlertDialogToast(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
        lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        alertDialog.getWindow().setAttributes(lp);
        window.setContentView(R.layout.dialog_toast);
        TextView tv_dialog = window.findViewById(R.id.dialog_text);
        tv_dialog.setText(message);
        TextView confirm = window.findViewById(R.id.confirm);
        TextView cancel = window.findViewById(R.id.cancel);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDialogListener.onConfirmClick();
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mOnDialogListener.onCancelClick();
                alertDialog.dismiss();
            }
        });
    }

    public  void setOnDialogListener(OnDialogListener onDialogListener) {
        this.mOnDialogListener = onDialogListener;
    }

    public interface OnDialogListener {
        void onConfirmClick();

        void onCancelClick();
    }
}
