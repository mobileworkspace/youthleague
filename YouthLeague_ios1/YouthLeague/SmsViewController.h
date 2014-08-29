//
//  SmsViewController.h
//  YouthLeague
//
//  Created by neusoft on 5/21/13.
//  Copyright (c) 2013 Allen. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HomeViewController.h"
#import <MessageUI/MFMessageComposeViewController.h>

@interface SmsViewController : UIViewController<MFMessageComposeViewControllerDelegate, UITextViewDelegate>

@property HomeViewController *delegate;

@property NSArray *staffArray;

@property (weak, nonatomic) IBOutlet UITextView *sendToTextView;
@property (weak, nonatomic) IBOutlet UITextView *sendMsgTextView;
@property (weak, nonatomic) IBOutlet UIButton *sendButton;

- (IBAction)sendSms:(UIButton *)sender;
- (IBAction)backButton:(UIBarButtonItem *)sender;
- (IBAction)backgroudTap:(id)sender;


@end
