//
//  MyNavigationController.m
//  InAppMiniProgram
//
//  Created by Kevin on 2020/1/9.
//  Copyright © 2020年 Facebook. All rights reserved.
//

#import "MyNavigationController.h"

@interface MyNavigationController ()

@end

@implementation MyNavigationController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (instancetype)initWithRootViewController:(UIViewController *)rootViewController
{
  self = [super initWithRootViewController:rootViewController];
  if (self) {
    //添加手势返回 IOS7及以上系统才能使用
    if([[[UIDevice currentDevice] systemVersion] floatValue] >= 7.0f){
      self.interactivePopGestureRecognizer.delegate = (id<UIGestureRecognizerDelegate>)self;
    }
  }
  
  return self;
}


#pragma mark - UIGestureRecognizerDelegate
- (BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer {
  if (gestureRecognizer == self.interactivePopGestureRecognizer) {
    // 屏蔽调用rootViewController的滑动返回手势
    if (self.viewControllers.count < 2 || self.visibleViewController == [self.viewControllers objectAtIndex:0]) {
      return NO;
    }
  }
  return YES;
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
