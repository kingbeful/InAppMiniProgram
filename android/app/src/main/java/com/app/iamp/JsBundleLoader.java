package com.app.iamp;

import android.app.Application;
import android.util.SparseArray;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;

import java.io.File;

public enum JsBundleLoader {
    INSTANCE;

    private SparseArray<String> loadedBundle = new SparseArray<>();
    private boolean isDev = false;
    private static final String BundleSuffix = ".android.bundle";

    public void load (Application application) {
//        load(application, null);
        isDev = ((ReactApplication) application).getReactNativeHost().getUseDeveloperSupport();
        createReactContext(application);
    }

    //    public void load(Application application, ReactContextCallBack callBack) {
//        if (jsState.isDev) {
//            if (callBack != null) callBack.onInitialized();
//            return;
//        }
//        createReactContext(application, () -> {
//            loadBundle(application);
//            if (callBack != null) callBack.onInitialized();
//        });
//    }
    private void createReactContext (Application application
//        , ReactContextCallBack callBack
    ) {
//    if (jsState.isDev) {
//        if(callBack != null) callBack.onInitialized();
//        return;
//    }
        final ReactInstanceManager manager = ((ReactApplication) application).getReactNativeHost().getReactInstanceManager();
        if (!manager.hasStartedCreatingInitialContext()) {
            manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                @Override
                public void onReactContextInitialized (ReactContext context) {
                    Logger.i("React Context Initialized");
                    manager.removeReactInstanceEventListener(this);
                }
            });
            manager.createReactContextInBackground();
        } else {
//            if (callBack != null) callBack.onInitialized();
        }
    }

    public String getBundlePath (Application application, String bundleName) {
        String path = getBundleDir(application) + bundleName;
        if (new File(path).exists()) {
            return path;
        } else {
            return null;
        }
    }

    private static String getBundleDir (Application application) {
        return application.getFilesDir() + File.separator;
    }

    public void loadMPBundle (Application application, String bundleName, boolean isFromAssets) {
        final ReactInstanceManager manager = ((ReactApplication) application).getReactNativeHost().getReactInstanceManager();
        if (manager != null) {
            ReactContext reactContext = manager.getCurrentReactContext();
            if (reactContext != null) {
                CatalystInstance catalystInstance = reactContext.getCatalystInstance();
                if (isFromAssets) {
                    fromAssets(application, catalystInstance, bundleName, false);
                } else {
                    // TODO: need test later, just to pass the compile.
                    fromFile(application, catalystInstance, bundleName, getBundleDir(application), true);
                }
            }
        }
    }

    private void fromAssets (Application application, CatalystInstance catalystInstance, String bundleName, boolean isSync) {
        if (hasBundle(bundleName) || isDev) {
            if (!isDev) {
                Logger.i("Bundle " + bundleName + " is already loaded");
            }
            return;
        }
        String source = bundleName;
        if (!bundleName.startsWith("assets://")) {
            source = "assets://" + bundleName;
        }
        if (!source.endsWith(BundleSuffix)) {
            source = source + BundleSuffix;
        }
        try {
            if (catalystInstance != null) {
                catalystInstance.loadScriptFromAssets(application.getAssets(), source, isSync);
                addBundle(bundleName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fromFile (Application application, CatalystInstance catalystInstance, String bundleName, String sourceUrl, boolean isSync) {
        if (hasBundle(sourceUrl) || isDev) {
            if (!isDev) {
                Logger.i("Bundle " + bundleName + " is already loaded");
            }
            return;
        }

        String bundleDir = getBundleDir(application);

        try {
            if (catalystInstance != null) {
                catalystInstance.loadScriptFromFile(bundleDir + File.separator + bundleName, sourceUrl, isSync);
                addBundle(sourceUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasBundle (String bundle) {
        return this.loadedBundle.indexOfValue(bundle) != -1;
    }

    private void addBundle (String bundle) {
        int index = this.loadedBundle.size();
        this.loadedBundle.put(index, bundle);
    }
}
