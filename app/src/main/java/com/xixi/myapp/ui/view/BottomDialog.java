package com.xixi.myapp.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xixi.myapp.R;


public class BottomDialog extends Dialog {
	private String TAG = "BottomDialog";

	public BottomDialog(Activity ctx, int layout, int width, int height) {
		this(ctx.getLayoutInflater().inflate(layout, null), width, height);
	}

	public BottomDialog(View view, int width, int height) {
		this(view, width, height, R.style.BottomDialog);
	}

	public BottomDialog(View view, int width, int height, int style) {
		super(view.getContext(), style);
		setContentView(view);

		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = width;
		params.height = height;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		window.setGravity(Gravity.BOTTOM);
	}

	public BottomDialog setOnClickListener(int id, View.OnClickListener listener) {
		View view = findViewById(id);
		if (view != null && listener != null)
			view.setOnClickListener(listener);
		return this;
	}

	public void setOutsideTouchable(boolean touchable) {
	}

	public void setBackgroundDrawable(Drawable background) {
	}

	public void setAnimationStyle(int animationStyle) {
	}

	public void showAtLocation(View parent, int gravity, int x, int y) {
		show();
	}
}
