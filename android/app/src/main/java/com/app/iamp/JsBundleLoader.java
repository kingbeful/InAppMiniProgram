package com.app.iamp;

import android.app.Application;
import android.os.AsyncTask;
import android.util.SparseArray;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public enum JsBundleLoader {
    INSTANCE;

    private SparseArray<String> loadedBundle = new SparseArray<>();
    private boolean isDev = false;
    private String bundleName;
    private String moduleName;
    private boolean loadFromAssets = true;
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

    public String getBundleDir (Application application) {
        return application.getFilesDir() + File.separator;
    }

    public void loadMPBundle (Application application, String bundleName) {
        final ReactInstanceManager manager = ((ReactApplication) application).getReactNativeHost().getReactInstanceManager();
        if (manager != null) {
            ReactContext reactContext = manager.getCurrentReactContext();
            if (reactContext != null) {
                CatalystInstance catalystInstance = reactContext.getCatalystInstance();
                if (this.loadFromAssets) {
                    fromAssets(application, catalystInstance, bundleName, false);
                } else {
                    // TODO: need test later, just to pass the compile.
                    fromFile(application, catalystInstance, bundleName, getBundleDir(application), false);
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
        if (!bundleName.endsWith(BundleSuffix)) {
            bundleName = bundleName + BundleSuffix;
        }

        try {
            if (catalystInstance != null) {
                catalystInstance.loadScriptFromFile(bundleDir + bundleName, sourceUrl, isSync);
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

    public String getBundleName () {
        return this.bundleName;
    }

    public JsBundleLoader setBundleName (String name) {
        this.bundleName = name;
        return this;
    }

    public String getModuleName () {
        return this.moduleName;
    }

    public JsBundleLoader setModuleName (String name) {
        this.moduleName = name;
        return this;
    }

    public JsBundleLoader setLoadStatus (boolean isFromAssets) {
        this.loadFromAssets = isFromAssets;
        return this;
    }

    public boolean getLoadStatus () {
        return this.loadFromAssets;
    }

    public void loadHttpBundle (Application application, String url, DownloadListener listener) {
        long tsLong = System.currentTimeMillis();
        if (url.startsWith("http://") || url.startsWith("https://")) {
            if (url.contains("?")) {
                url = url + "&ts=" + tsLong;
            } else {
                url = url + "?ts=" + tsLong;
            }
        }
        new DownloadFileFromURL(application, listener).execute(url);
    }

    public interface DownloadListener {
        void onStart ();

        void onFinish ();
    }

    /**
     * Background Async Task to download file
     */
    static class DownloadFileFromURL extends AsyncTask<String, String, String> {
        private DownloadListener listener;
        private String bundleDir;

        public DownloadFileFromURL (Application application, DownloadListener listener) {
            this.listener = listener;
            this.bundleDir = JsBundleLoader.INSTANCE.getBundleDir(application);
        }

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            this.listener.onStart();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground (String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lengthOfFile = connection.getContentLength();
                String[] sections = f_url[0].split("/");
                // ToDo: maybe should check the sections length
                String fileName = sections[sections.length - 1];
                if (fileName.contains("?")) {
                    fileName = fileName.split("\\?")[0];
                }


                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(this.bundleDir + fileName);
                Logger.i("Download Output: " + this.bundleDir + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Logger.e(e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate (String... progress) {
            // setting progress percentage
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute (String file_url) {
            // dismiss the dialog after the file was downloaded
            this.listener.onFinish();
        }

    }
}
