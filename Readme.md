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


## Todos

1. add test case
2. UI editor tool
3. hot load the business logic from cloud

## Reference

https://github.com/yxyhail/MetroExample

https://juejin.im/post/5cee0095f265da1b6d4006ec

https://www.jianshu.com/p/3013e3b9ebd7


## License MIT