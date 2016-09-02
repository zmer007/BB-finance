package com.rubo.dfer.assistant.data.entry;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-29
 * Function:
 */
public class Check {
    String message;
    boolean isChecked;

    public Check(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
