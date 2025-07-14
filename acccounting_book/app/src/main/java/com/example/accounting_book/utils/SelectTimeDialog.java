package com.example.accounting_book.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.accounting_book.R;


/*
* 在记录页面弹出时间对话框
* */
public class SelectTimeDialog extends Dialog implements View.OnClickListener {
    EditText hourEt,minuteEt;
    DatePicker datePicker;
    Button ensureBtn,cancelBtn;
    public interface OnEnsureListener{
        public void onEnsure(String time,int year,int month,int day);
    }
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        hourEt = findViewById(R.id.dialog_time_et_hour);
        minuteEt = findViewById(R.id.dialog_time_et_minute);
        datePicker = findViewById(R.id.dialog_time_dp);
        ensureBtn = findViewById(R.id.dialog_time_btn_ensure);
        cancelBtn = findViewById(R.id.dialog_time_btn_cancel);
        ensureBtn.setOnClickListener(this);  //添加点击监听事件
        cancelBtn.setOnClickListener(this);
        hideDatePickerHeader();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.dialog_time_btn_cancel) {
            cancel();  // 关闭对话框
        } else if (id == R.id.dialog_time_btn_ensure) {
            // 获取日期
            int year = datePicker.getYear();
            int month = datePicker.getMonth() + 1;
            int day = datePicker.getDayOfMonth();

            // 获取小时和分钟，处理空值和合法性
            int hour = parseEditText(hourEt, 0, 23);
            int minute = parseEditText(minuteEt, 0, 59);

            // 格式化为字符串（自动补零）
            String timeFormat = String.format("%d年%02d月%02d日 %02d:%02d", year, month, day, hour, minute);

            // 回调传出结果
            if (onEnsureListener != null) {
                onEnsureListener.onEnsure(timeFormat, year, month, day);
            }

            cancel();  // 关闭对话框
        }
    }

    /**
     * 将 EditText 中的文本转换为 int，限制范围，默认值为 0
     */
    private int parseEditText(EditText editText, int min, int max) {
        String str = editText.getText().toString().trim();
        int value = 0;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                value = 0; // 出错就设为0
            }
        }
        // 限制合法范围
        return Math.max(min, Math.min(value, max));
    }


    //隐藏DatePicker头布局
    private void hideDatePickerHeader(){
        ViewGroup rootView = (ViewGroup) datePicker.getChildAt(0);
        if (rootView == null) {
            return;
        }
        View headerView = rootView.getChildAt(0);
        if (headerView == null) {
            return;
        }
        //5.0+
        int headerId = getContext().getResources().getIdentifier("day_picker_selector_layout", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParamsRoot = rootView.getLayoutParams();
            layoutParamsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            rootView.setLayoutParams(layoutParamsRoot);

            ViewGroup animator = (ViewGroup) rootView.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(layoutParamsAnimator);

            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(layoutParamsChild);
            return;
        }

        // 6.0+
        headerId = getContext().getResources().getIdentifier("date_picker_header","id","android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
        }
    }
}
