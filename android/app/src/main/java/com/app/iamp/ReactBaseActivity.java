package com.app.iamp;

import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;

public class ReactBaseActivity extends ReactActivity {
    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new ReactActivityDelegate(this, getMainComponentName()) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                JsBundleLoader.INSTANCE.loadMPBundle(getApplication(), "guide", true);
                super.onCreate(savedInstanceState);
            }
        };
    }

    @Override
    protected String getMainComponentName() {
        return "InAppMiniProgram";
    }
}
