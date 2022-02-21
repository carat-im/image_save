package com.samoy.image_save;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.plugin.common.MethodChannel.Result;

class ThreadSafeResult implements Result {
    private final Result result;
    private final Handler handler;

    ThreadSafeResult(@NonNull Result result) {
        this.result = result;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void success(@Nullable final Object obj) {
        handler.post(new Runnable() {
            @Override
            public void run() {
              try {
                result.success(obj);
              } catch (Exception ignored) {}
            }
        });
    }

    @Override
    public void error(final String errorCode, @Nullable final String errorMessage, @Nullable final Object errorDetails) {
        handler.post(new Runnable() {
            @Override
            public void run() {
              try {
                result.error(errorCode, errorMessage, errorDetails);
              } catch (Exception ignored) {}
            }
        });
    }

    @Override
    public void notImplemented() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                result.notImplemented();
            }
        });
    }
}
