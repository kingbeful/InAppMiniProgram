//
//  StartViewController.m
//  InAppMiniProgram
//
//  Created by Kevin on 2020/1/7.
//  Copyright © 2020年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RCTBridge.h"
#import <React/RCTBridge+Private.h>
#import <React/RCTRootView.h>
#import "RNViewController.h"
#import "StartViewController.h"

#define WIDTH [[UIScreen mainScreen] bounds].size.width
#define HEIGHT [[UIScreen mainScreen] bounds].size.height

@interface StartViewController () <UITableViewDelegate, UITableViewDataSource>

@property(strong, nonatomic) UITableView * tableView;
@property(strong, nonatomic) NSArray * dataSource;
@property(strong, nonatomic) NSDictionary * isBundleLoaded;

@end

@implementation StartViewController
{
  RCTBridge *_bridge;
}

- (instancetype)initWithBridge:(RCTBridge *)bridge {
  if (self = [super init]) {
    _bridge = bridge;
    _isBundleLoaded = [[NSMutableDictionary alloc] init];
  }
  return self;
}
- (void)viewDidLoad {
  [super viewDidLoad];
  self.view.backgroundColor =[UIColor whiteColor];

  _dataSource = @[
      @{@"title":@"react native", @"subtitle": @"a react native quick starter", @"moduleName": @"InAppMiniProgram", @"bundleName": @"guide"},
      @{@"title":@"museum local", @"subtitle": @"load the bundle from local file system", @"moduleName": @"museum", @"bundleName": @"museum"},
      @{@"title":@"museum http", @"subtitle": @"load the bundle from remote server", @"moduleName": @"museum", @"bundleName": @"museum"},
      @{@"title":@"navigation", @"subtitle": @"react navigation sample", @"moduleName": @"navigation", @"bundleName": @"navigation"}
  ];
  NSLog(@"viewDidLoad");
  self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, WIDTH, HEIGHT) style:UITableViewStylePlain];
  _tableView.delegate = self;
  _tableView.dataSource = self;
  _tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
  [self.view addSubview:_tableView];

  
}

#pragma mark - UITableViewDataSource && Delegate

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
  return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
  return _dataSource.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
  
  static NSString *CellIdentifier = @"Cell";
  UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
  if (cell == nil) {
    cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
  }
  // Configure the cell.
  cell.textLabel.text = [_dataSource[indexPath.row] objectForKey:@"title"];
  cell.detailTextLabel.text = [_dataSource[indexPath.row] objectForKey:@"subtitle"];
  cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
  return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
  NSString * moduleName = [_dataSource[indexPath.row] objectForKey:@"moduleName"];
  NSString * bundleName = [_dataSource[indexPath.row] objectForKey:@"bundleName"];
  if (![_isBundleLoaded objectForKey:bundleName]) {
    // bundle is not loaded
    NSURL *jsCodeLocation = [[NSBundle mainBundle] URLForResource:bundleName withExtension:@"bundle"];
    NSError *error = nil;
    NSData *sourceBuz = [NSData dataWithContentsOfFile:jsCodeLocation.path
                                               options:NSDataReadingMappedIfSafe
                                                 error:&error];
    [_bridge.batchedBridge executeSourceCode:sourceBuz sync:NO];
    [_isBundleLoaded setValue:moduleName forKey:bundleName];
  }
  
  RNViewController *rootViewController = [RNViewController new];
  RCTRootView* rootView = [[RCTRootView alloc] initWithBridge:_bridge moduleName:moduleName initialProperties:nil];
  
  rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];
  rootViewController.view = rootView;
  [self.navigationController pushViewController:rootViewController animated:YES];
  [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

@end
