//
//  HBDNavigator.h
//
//  Created by Listen on 2017/11/25.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "HBDReactBridgeManager.h"

UIKIT_EXTERN NSInteger const RESULT_OK;
UIKIT_EXTERN NSInteger const RESULT_CANCEL;
UIKIT_EXTERN NSString * const ON_COMPONENT_RESULT_EVENT;
UIKIT_EXTERN NSString * const ON_BAR_BUTTON_ITEM_CLICK_EVENT;

@class HBDReactBridgeManager;
@class HBDViewController;
@class HBDNavigationController;

@interface HBDNavigator : NSObject

+ (HBDNavigator *)navigatorForId:(NSString *)navId;

@property(nonatomic, copy, readonly) NSString *navId;
@property(nonatomic, weak) UINavigationController *navigationController;

- (HBDViewController *)controllerWithModuleName:(NSString *)moduleName props:(NSDictionary *)props options:(NSDictionary *)options;

- (void)pushModule:(NSString *)moduleName props:(NSDictionary *)props options:(NSDictionary *)options animated:(BOOL) animated;

- (void)pushModule:(NSString *)moduleName;

- (BOOL)canPop;

- (void)popAnimated:(BOOL)animated;

- (void)popToScene:(NSString *)sceneId animated:(BOOL)animated;

- (void)popToRootAnimated:(BOOL)animated;

- (void)replaceModule:(NSString *)moduleName props:(NSDictionary *)props options:(NSDictionary *)options;

- (void)replaceModule:(NSString *)moduleName;

- (void)replaceToRootModule:(NSString *)moduleName props:(NSDictionary *)props options:(NSDictionary *)options;

- (void)replaceToRootModule:(NSString *)moduleName;

- (void)presentModule:(NSString *)moduleName requestCode:(NSInteger) requestCode props:(NSDictionary *)props options:(NSDictionary *)options animated:(BOOL) animated;

- (void)presentModule:(NSString *)moduleName requestCode:(NSInteger) requestCode;

- (void)setResultCode:(NSInteger)resultCode data:(NSDictionary *)data;

- (BOOL)canDismiss;

- (void)dismissAnimated:(BOOL)animated;

- (UIViewController *)topViewController;

@end
