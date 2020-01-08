#! /bin/bash
echo Running bundle script...
echo Packing for $1

#react-native bundle --platform android --dev false --entry-file index.js --bundle-output ./android/app/src/main/assets/index.android.bundle  --config metro.config.js

if [ $1 == "android" ]; then
    # create the basic bundle
    react-native bundle --platform $1 --dev false --entry-file src/basics.js --bundle-output ./android/app/src/main/assets/index.android.bundle --assets-dest android/app/src/main/res/ --config basics.config.js

    # create the business bundle --- react native guide
    react-native bundle --platform $1 --dev false --entry-file src/guide/index.js --bundle-output ./android/app/src/main/assets/guide.android.bundle --assets-dest android/app/src/main/res/ --config business.config.js

    # create the business bundle --- museum
    react-native bundle --platform $1 --dev false --entry-file src/museum/index.js --bundle-output ./android/app/src/main/assets/museum.android.bundle --assets-dest android/app/src/main/res/ --config business.config.js

    # create the business bundle --- navigation
    react-native bundle --platform $1 --dev false --entry-file src/navigation/index.js --bundle-output ./android/app/src/main/assets/navigation.android.bundle --assets-dest android/app/src/main/res/ --config business.config.js
else
    # create the basic bundle
    react-native bundle --platform $1 --dev false --entry-file src/basics.js --bundle-output ./ios/InAppMiniProgram/index.android.bundle --assets-dest ios/InAppMiniProgram/ --config basics.config.js

    # create the business bundle --- react native guide
    react-native bundle --platform $1 --dev false --entry-file src/guide/index.js --bundle-output ./ios/InAppMiniProgram/guide.android.bundle --assets-dest ios/InAppMiniProgram/ --config business.config.js

    # create the business bundle --- museum
    react-native bundle --platform $1 --dev false --entry-file src/museum/index.js --bundle-output ./ios/InAppMiniProgram/museum.android.bundle --assets-dest ios/InAppMiniProgram/ --config business.config.js

    # create the business bundle --- navigation
    react-native bundle --platform $1 --dev false --entry-file src/navigation/index.js --bundle-output ./ios/InAppMiniProgram/navigation.android.bundle --assets-dest ios/InAppMiniProgram/ --config business.config.js
fi