package com.navigationhybrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import javax.annotation.Nullable;

/**
 * Created by Listen on 2017/11/22.
 */

public class TopBar extends Toolbar {

    private TextView titleView;

    private TextView leftButton;

    private TextView rightButton;

    private Drawable divider;

    private int contentInset;

    public TopBar(Context context) {
        super(context);
        init(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        leftButton = new TextView(context);
        leftButton.setGravity(Gravity.CENTER);
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(-2, -1, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        addView(leftButton, layoutParams);

        rightButton = new TextView(context);
        rightButton.setGravity(Gravity.CENTER);
        layoutParams = new Toolbar.LayoutParams(-2, -1, Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        addView(rightButton, layoutParams);

        titleView = new TextView(context);
        layoutParams = new Toolbar.LayoutParams(-2, -2, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        addView(titleView, layoutParams);

        contentInset = getContentInsetStart();
        setContentInsetStartWithNavigation(getContentInsetStartWithNavigation() - contentInset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (divider != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int height = (int) getContext().getResources().getDisplayMetrics().density;
            divider.setBounds(0, getHeight() - height, getWidth(), getHeight());
            divider.draw(canvas);
        }
    }

    public void setShadow(@Nullable Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            divider = drawable;
            postInvalidate();
        }
    }

    public void hideShadow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(0);
        } else {
            setShadow(null);
        }
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getLeftButton() {
        return leftButton;
    }

    public TextView getRightButton() {
        return rightButton;
    }

    public void setLeftButton(Drawable icon, String title, boolean enabled) {
        setContentInsetsRelative(0, getContentInsetEnd());
        setNavigationIcon(null);
        setNavigationOnClickListener(null);
        setButton(leftButton, icon, title, enabled);
    }

    public void setRightButton(Drawable icon, String title, boolean enabled) {
        setContentInsetsRelative(getContentInsetStart(), 0);
        setButton(rightButton, icon, title, enabled);
    }

    void setButton(TextView button, Drawable icon, String title, boolean enabled) {
        button.setOnClickListener(null);
        button.setText(null);
        button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        button.setMaxWidth(Integer.MAX_VALUE);
        button.setAlpha(1.0f);
        button.setVisibility(View.VISIBLE);

        int color = Garden.getBarButtonItemTintColor();
        if (!enabled) {
            color = StyleUtils.generateGrayColor(color);
            button.setAlpha(0.3f);
        }
        button.setEnabled(enabled);

        if (icon != null) {
            icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            int width = getContentInsetStartWithNavigation();
            int padding = (width - icon.getIntrinsicWidth()) / 2;
            button.setMaxWidth(width);
            button.setPaddingRelative(padding, 0, padding, 0);
        } else {
            int padding = getContentInset();
            button.setPaddingRelative(padding, 0, padding, 0);
            button.setText(title);
            button.setTextColor(color );
            button.setTextSize(Garden.getBarButtonItemTextSizeDp());
        }

        TypedValue typedValue = new TypedValue();
        if (getContext().getTheme().resolveAttribute(R.attr.actionBarItemBackground, typedValue, true)) {
            button.setBackgroundResource(typedValue.resourceId);
        }
    }


    protected int getContentInset() {
        return this.contentInset;
    }

    public void setTitleViewAlignment(String alignment) {
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
        if ("center".equals(alignment)) {
            layoutParams.gravity = Gravity.CENTER;
        } else {
            layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;

        }
        titleView.setLayoutParams(layoutParams);
    }

}
