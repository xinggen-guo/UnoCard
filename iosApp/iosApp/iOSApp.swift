import SwiftUI
import shared

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    init(){
        ObjectUnoCommon.shared.initializeGame()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
