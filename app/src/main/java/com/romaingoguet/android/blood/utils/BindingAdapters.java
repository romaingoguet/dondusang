package com.romaingoguet.android.blood.utils;

import androidx.databinding.BindingAdapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

public class BindingAdapters {

    @BindingAdapter("app:iconswitch")
    public static void setImageResource(ImageView imageView, Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter("android:onClick")
    public static void bindingListener(final View view, final View.OnClickListener listener) {
        RxView.clicks(view)
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe(
                        ignore -> listener.onClick(view),
                        error -> Log.d("lol", "bindingListener: error")
                );
    }

    @BindingAdapter("app:error")
    public static void setErrorMessage(final TextInputLayout view, String message) {
        view.setError(message);
    }
}
