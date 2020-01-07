package com.app.iamp;

import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;

public class ReactBaseActivity extends ReactActivity {

    @Override
    protected ReactActivityDelegate createReactActivityDelegate () {
        return new ReactActivityDelegate(this, getMainComponentName()) {
            @Override
            protected void onCreate (Bundle savedInstanceState) {
                JsBundleLoader.INSTANCE.loadMPBundle(getApplication(), getBundleName());
                super.onCreate(savedInstanceState);
            }
            @Override
            protected ReactRootView createRootView () {
                return new RNGestureHandlerEnabledRootView(ReactBaseActivity.this);
            }
        };
    }

    @Override
    protected String getMainComponentName () {
        String name = JsBundleLoader.INSTANCE.getModuleName();
        if (name != null) {
            return name;
        } else {
            return "InAppMiniProgram";
        }
    }

    protected String getBundleName () {
        String name = JsBundleLoader.INSTANCE.getBundleName();
        if (name != null) {
            return name;
        } else {
            return "guide";
        }
    }
}
