package com.rubo.dfer.assistant.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;
import android.widget.TextView;

import com.rubo.dfer.assistant.MainApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-30
 * Function:
 */
public class SpanUtils {
    public static void setTextViewSpan(TextView tv, String regex) {
        Typeface tf = Typeface.createFromAsset(MainApplication.getInstance().getAssets(), "font/bottles.ttf");
        String rawStr = tv.getText().toString();
        SpannableString sp = new SpannableString(rawStr);
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(rawStr);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            sp.setSpan(new CustomTypefaceSpan("", tf, 50), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(sp);
    }

    static class CustomTypefaceSpan extends TypefaceSpan {

        private final Typeface newType;
        private final int textSize;

        public CustomTypefaceSpan(String family, Typeface type, int textSize) {
            super(family);
            newType = type;
            this.textSize = textSize;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setTextSize(textSize);
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            paint.setTextSize(textSize);
            applyCustomTypeFace(paint, newType);
        }

        private static void applyCustomTypeFace(Paint paint, Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }
    }
}
