# InAppMiniProgram

------------------------

## Introduction

This project is based on react native. The aim of this project is helping you to create an app which has the ability to maintain your own mini-programs.

The whole app is divided into several mini-programs, inculdes bussiness logic and the resources.

The benifit is these mini-programs have tiny dependency logic with others(only share the basic infos, such as user name/token/id/avatar). 


## Concept

As we all know, react native creates a js bundle when it releasing an app. When app logic become more complicated, the size of bundle is increasing day by day. The size of the bundle will become the bottleneck when app cold-starting as the screen will be white for some seconds when it loading the js bundle.

To avoid this, in this project we divide the logic into multi-bundles: 

 1. basic bundle only includes the basic library of react native. 

 2. business bundle is used for each mini-program

When app startup, it will load the basic bundle in background. And the first screen is wrote with native code (java/ObjectiveC), that will speedup the start time.

When user click on the native UI and the screen changing to the one of the business screen. The business screen will load business bundle. The size is much smaller than the basic bundle and will load very fast.

## Tricks

1. Android app to debug with the bundle in assets. You need to change the return value of **getUseDeveloperSupport** to false, and comment the line **apply from: "../../node_modules/react-native/react.gradle"** in app/build.gradle

2. Using the bundle.sh to generate all the bundles. To generate the platform bundle you should use the script like below before install the app.

```bash
# to generate the ios bundle
./bundle.sh ios

# to generate the android bundle
./bundle.sh android

```

3. To debug the iOS app and load the data from the local server, you should uncomment the code in two files

**StartViewController.m**

```objective-c

...

#if DEBUG
//#else             <- uncomment this line
  if (![_isBundleLoaded objectForKey:bundleName]) {

...

```

**AppDelegate.m**

```objective-c

...

#if DEBUG
// uncomment below 2 lines
//   jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index" fallbackResource:nil];
// #else
  jsCodeLocation = [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
#endif

...

```

after doing these 2 steps, start the react native server by command: **react-native start** and then press Command + R to build the iOS app


## FAQ

### iOS

- Why I got error message **Failed to execute 'importScripts' on 'WorkerGlobalScope' when using main.jsbundle on iOS simulator** ?

A: That's because you want debug the offline bundle in chrome. Please Command + D to disable it, and then rerun the app with Command + R


### android

## Todos

1. add test case
2. UI editor tool
3. hot load the business logic from cloud

## Reference

https://github.com/yxyhail/MetroExample

https://juejin.im/post/5cee0095f265da1b6d4006ec

https://www.jianshu.com/p/3013e3b9ebd7

https://juejin.im/post/5ce79475f265da1b860861fd

https://github.com/smallnew/react-native-multibundler


## License MIT

Copyright 2020 Kevin Qian <kevin.qian@msn.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.