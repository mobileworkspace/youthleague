//
//  SmsViewController.m
//  YouthLeague
//
//  Created by neusoft on 5/21/13.
//  Copyright (c) 2013 Allen. All rights reserved.
//

#import "SmsViewController.h"
#import "Staff.h"

@interface SmsViewController ()
{
    NSMutableString *sendToString;
    NSMutableArray *sendToArray;
}
@end

@implementation SmsViewController

@synthesize staffArray;
@synthesize sendMsgTextView, sendToTextView, sendButton;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
//    float version = [[[UIDevice currentDevice] systemVersion] floatValue];
//
//    if (version < 5) {
    
        [sendButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                               forState:UIControlStateNormal];
        [sendButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn_pressed"] stretchableImageWithLeftCapWidth:12.0 topCapHeight:20.0]
                               forState:UIControlStateHighlighted];
        
//    }else{
//        
//        [sendButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
//                               forState:UIControlStateNormal];
//        [sendButton setBackgroundImage:[[UIImage imageNamed:@"ic_green_btn_pressed"] resizableImageWithCapInsets:UIEdgeInsetsMake(20.0, 12.0, 20.0, 12.0) resizingMode:UIImageResizingModeStretch]
//                               forState:UIControlStateHighlighted];
//
//    }
    
    sendToString = [[NSMutableString alloc] init];
    sendToArray = [[NSMutableArray alloc] init];
    
    for (Staff *staff in staffArray) {
        [sendToString appendFormat:@"%@<%@>,", staff.name, staff.mobile];
        [sendToArray addObject:staff.mobile];
    }
    
    sendToTextView.text = [sendToString substringToIndex:sendToString.length-1];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(void)textViewDidBeginEditing:(UITextView *)textView
{
    CGRect curFrame = self.view.frame;
    [UIView animateWithDuration:0.3f animations:^{
        self.view.frame = CGRectMake(curFrame.origin.x, curFrame.origin.y - 213, curFrame.size.width, curFrame.size.height);
    }];
//    [UIView beginAnimations:nil context:nil];
//    [UIView setAnimationDuration:0.25];
//    [self.view setFrame:CGRectMake(0, curFrame.origin.y - 216, self.view.frame.size.width, self.view.frame.size.height)];
//    [UIView commitAnimations];
}

-(void)textViewDidEndEditing:(UITextView *)textView
{
    CGRect curFrame = self.view.frame;
    [UIView animateWithDuration:0.3f animations:^{
        self.view.frame = CGRectMake(curFrame.origin.x, curFrame.origin.y + 213, curFrame.size.width, curFrame.size.height);
    }];
}

-(void)messageComposeViewController:(MFMessageComposeViewController *)controller didFinishWithResult:(MessageComposeResult)result
{
    UIAlertView *alertView;
    
    switch (result)
    {
        case MessageComposeResultCancelled:
            
            break;
            
        case MessageComposeResultSent:
            alertView = [[UIAlertView alloc] initWithTitle:@"短信发送成功" message:nil delegate:nil cancelButtonTitle:@"关闭" otherButtonTitles:nil];
            [alertView show];
            break;
            
        case MessageComposeResultFailed:
            alertView = [[UIAlertView alloc] initWithTitle:@"短信发送失败" message:nil delegate:nil cancelButtonTitle:@"关闭" otherButtonTitles:nil];
            [alertView show];
            break;

    }
    
    if ([self respondsToSelector:@selector(dismissViewControllerAnimated:completion:)]) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }else{
        [self dismissModalViewControllerAnimated:YES];
    }
}

- (IBAction)sendSms:(UIButton *)sender
{
    BOOL canSendSMS = [MFMessageComposeViewController canSendText];
    
    NSLog(@"can send SMS [%d]",canSendSMS);
    
    if (canSendSMS) {
        
        MFMessageComposeViewController *picker = [[MFMessageComposeViewController alloc] init];
        picker.messageComposeDelegate = self;
        picker.navigationBar.tintColor = [UIColor blackColor];
        picker.body = sendMsgTextView.text;
        picker.recipients = sendToArray;
        
        if ([self respondsToSelector:@selector(presentViewController:animated:completion:)]) {
            [self presentViewController:picker animated:YES completion:nil];   // ios 5 and 以上
        }else{
            [self presentModalViewController:picker animated:YES];    // ios 4 and  以下
        }
        
    }
}

- (IBAction)backButton:(UIBarButtonItem *)sender
{
    self.delegate.isNeedOpen = NO;
    
    if ([self respondsToSelector:@selector(dismissViewControllerAnimated:completion:)]) {
        [self dismissViewControllerAnimated:YES completion:nil];
    }else{
        [self dismissModalViewControllerAnimated:YES];
    }
}

- (IBAction)backgroudTap:(id)sender {
    [self.sendMsgTextView resignFirstResponder];
    [self.sendToTextView resignFirstResponder];  
}

- (void)viewDidUnload {
    [self setSendToTextView:nil];
    [self setSendMsgTextView:nil];
    [self setSendButton:nil];
    [super viewDidUnload];
}
@end
