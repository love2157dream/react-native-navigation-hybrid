//
//  HBDReactViewController.m
//
//  Created by Listen on 2017/11/26.
//

#import "HBDReactViewController.h"
#import "HBDReactBridgeManager.h"

#import <React/RCTRootView.h>
#import <React/RCTEventEmitter.h>
#import <React/RCTConvert.h>

@interface HBDReactViewController ()

@property(nonatomic, copy) NSString *moduleName;

@end

@implementation HBDReactViewController

- (instancetype)initWithNavigator:(HBDNavigator *)navigator moduleName:(NSString *)moduleName props:(NSDictionary *)props options:(NSDictionary *)options {
    if (self = [super initWithNavigator:navigator props:props options:options]) {
        _moduleName = moduleName;
    }
    return self;
}

- (void)loadView {
    RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:[HBDReactBridgeManager instance].bridge moduleName:self.moduleName initialProperties:self.props];
    self.view = rootView;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    //NSLog(@"viewWillAppear");
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    //NSLog(@"viewDidAppear");
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    //NSLog(@"viewWillDisappear");
}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
    //NSLog(@"viewDidDisappear");
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)didReceiveResultCode:(NSInteger)resultCode resultData:(NSDictionary *)data requestCode:(NSInteger)requestCode {
    [super didReceiveResultCode:resultCode resultData:data requestCode:requestCode];
    RCTEventEmitter *emitter = [[HBDReactBridgeManager instance].bridge moduleForName:@"NavigationHybrid"];
    [emitter sendEventWithName:ON_COMPONENT_RESULT_EVENT body:@{@"requestCode": @(requestCode),
                                                                @"resultCode": @(resultCode),
                                                                @"data": data ?: [NSNull null],
                                                                @"navId": self.navigator.navId,
                                                                @"sceneId": self.sceneId,
                                                                }];
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
