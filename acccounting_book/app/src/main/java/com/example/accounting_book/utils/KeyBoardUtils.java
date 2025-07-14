package com.example.accounting_book.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.accounting_book.R;

public class KeyBoardUtils {
    private final Keyboard k1; // 自定义键盘
    private final KeyboardView keyboardView;
    private final EditText editText;

    // 点击“保存”键时的回调接口
    public interface OnEnsureListener {
        void onEnsure();
    }
    private OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    // 构造方法
    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;

        // 禁用系统键盘
        this.editText.setInputType(InputType.TYPE_NULL);

        // 加载自定义键盘 XML（这里替换成你的 XML 名称）
        k1 = new Keyboard(this.editText.getContext(), R.xml.key);

        this.keyboardView.setKeyboard(k1);
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false); // 不显示点击预览气泡
        this.keyboardView.setOnKeyboardActionListener(listener);
    }

    // 核心：按键监听器
    private final KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {}
        @Override
        public void onRelease(int primaryCode) {}

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();

            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE: // 删除键 (-5)
                    if (editable != null && editable.length() > 0 && start > 0) {
                        editable.delete(start - 1, start);
                    }
                    break;

                case Keyboard.KEYCODE_CANCEL: // 「每记」键 (-3)
                    editable.clear(); // 清空输入
                    break;

                case Keyboard.KEYCODE_DONE: // 「保存」键 (-4)
                    if (onEnsureListener != null) {
                        String expr = editable.toString();
                        String result = evaluateExpression(expr); // 计算结果
                        editable.clear();
                        editable.append(result); // 显示结果
                        onEnsureListener.onEnsure();
                    }
                    break;

                // 数字、+、-、. 键：直接插入
                case 43: // '+'
                case 45: // '-'
                case 46: // '.'
                case 48: // '0'
                case 49: // '1'
                case 50: // '2'
                case 51: // '3'
                case 52: // '4'
                case 53: // '5'
                case 54: // '6'
                case 55: // '7'
                case 56: // '8'
                case 57: // '9'
                    editable.insert(start, Character.toString((char) primaryCode));
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onText(CharSequence text) {}
        @Override
        public void swipeLeft() {}
        @Override
        public void swipeRight() {}
        @Override
        public void swipeDown() {}
        @Override
        public void swipeUp() {}
    };

    // 简单表达式计算器（只支持整数加减）
    private String evaluateExpression(String expr) {
        try {
            if (expr.isEmpty()) return "0";

            String[] nums = expr.split("[-+]");
            int result = Integer.parseInt(nums[0]);
            int index = nums[0].length();

            for (int i = 1; i < nums.length; i++) {
                if (index >= expr.length()) break;
                char op = expr.charAt(index);
                int num = Integer.parseInt(nums[i]);
                if (op == '+') {
                    result += num;
                } else if (op == '-') {
                    result -= num;
                }
                index += 1 + nums[i].length();
            }
            return String.valueOf(result);
        } catch (Exception e) {
            return "错误";
        }
    }

    // 显示键盘
    public void showKeyboard() {
        if (keyboardView.getVisibility() == View.INVISIBLE || keyboardView.getVisibility() == View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    // 隐藏键盘
    public void hideKeyboard() {
        if (keyboardView.getVisibility() == View.VISIBLE || keyboardView.getVisibility() == View.INVISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }
}
