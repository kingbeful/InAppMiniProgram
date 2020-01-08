//
//  StartViewController.m
//  InAppMiniProgram
//
//  Created by Kevin on 2020/1/7.
//  Copyright © 2020年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "StartViewController.h"

#define WIDTH [[UIScreen mainScreen] bounds].size.width
#define HEIGHT [[UIScreen mainScreen] bounds].size.height



@interface StartViewController () <UITableViewDelegate, UITableViewDataSource>

@property(strong, nonatomic) UITableView * tableView;
@property(strong, nonatomic) NSArray * dataSource;

@end

@implementation StartViewController

- (void)viewDidLoad {
  [super viewDidLoad];
  self.view.backgroundColor =[UIColor whiteColor];

  _dataSource = @[
      @{@"title":@"react native", @"subtitle": @"a react native quick starter"},
      @{@"title":@"museum local", @"subtitle": @"load the bundle from local file system"},
      @{@"title":@"museum http", @"subtitle": @"load the bundle from remote server"},
      @{@"title":@"navigation", @"subtitle": @"react navigation sample"}
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
  [tableView deselectRowAtIndexPath:indexPath animated:NO];
}


@end
