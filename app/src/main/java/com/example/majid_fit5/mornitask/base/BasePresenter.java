package com.example.majid_fit5.mornitask.base;

import android.support.annotation.NonNull;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

// This is the base presenter for all presenters in the app.
public interface BasePresenter<V extends BaseView> {
    void onBind(@NonNull V view);
    void onDestroy();
}
