package com.example.accounting_book.utils;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;

import com.example.accounting_book.AboutActivity;
import com.example.accounting_book.HistoryActivity;
import com.example.accounting_book.MonthChartActivity;
import com.example.accounting_book.R;
import com.example.accounting_book.SettingActivity;


public class MoreDialog extends Dialog implements View.OnClickListener {
    Button aboutBtn,settingBtn,historyBtn,infoBtn;
    ImageView errorIv;
    public MoreDialog(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_more);
        aboutBtn = findViewById(R.id.dialog_more_btn_about);
        settingBtn = findViewById(R.id.dialog_more_btn_setting);
        historyBtn = findViewById(R.id.dialog_more_btn_record);
        infoBtn = findViewById(R.id.dialog_more_btn_info);
        errorIv = findViewById(R.id.dialog_more_iv);

        aboutBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);
        errorIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int id = v.getId();

        if (id == R.id.dialog_more_btn_about) {
            intent.setClass(getContext(), AboutActivity.class);
            getContext().startActivity(intent);
        } else if (id == R.id.dialog_more_btn_setting) {
            intent.setClass(getContext(), SettingActivity.class);
            getContext().startActivity(intent);
        } else if (id == R.id.dialog_more_btn_record) {
            intent.setClass(getContext(), HistoryActivity.class);
            getContext().startActivity(intent);
        } else if (id == R.id.dialog_more_btn_info) {
            intent.setClass(getContext(), MonthChartActivity.class);
            getContext().startActivity(intent);
        } else if (id == R.id.dialog_more_iv) {
            // 可选操作：目前点击图标不执行任何操作
        }

        cancel();  // 点击任何一个按钮后关闭对话框
    }


    /* 设置Dialog的尺寸和屏幕尺寸一致*/
    public void setDialogSize(){
//        获取当前窗口对象
        Window window = getWindow();
//        获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}
