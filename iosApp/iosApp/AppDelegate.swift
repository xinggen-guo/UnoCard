//
//  AppDelegate.swift
//  iosApp
//
//  Created by xinggen guo on 2025/8/16.
//  Copyright © 2025 orgName. All rights reserved.
//

import UIKit

class AppDelegate: NSObject, UIApplicationDelegate {
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        
        // ✅ 设置全局异常捕获
        NSSetUncaughtExceptionHandler { exception in
            print("💥 Uncaught Objective-C Exception: \(exception)")
            print("💥 Stack Trace: \(exception.callStackSymbols)")
        }
        
        signal(SIGABRT) { signal in
            print("💥 Caught signal SIGABRT")
        }
        signal(SIGSEGV) { signal in
            print("💥 Caught signal SIGSEGV")
        }

        return true
    }
}
