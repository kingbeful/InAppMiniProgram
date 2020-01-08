//
//  RCTBridge.h
//  InAppMiniProgram
//
//  Created by Kevin on 2020/1/8.
//  Copyright © 2020年 Facebook. All rights reserved.
//


#import <Foundation/Foundation.h>
#import <React/RCTBridge.h>

@interface RCTBridge (RnLoadJS) // RN私有类 ，这里暴露他的接口

- (void)executeSourceCode:(NSData *)sourceCode sync:(BOOL)sync;

@end
