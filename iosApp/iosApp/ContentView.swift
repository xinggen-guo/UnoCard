import SwiftUI
import shared

struct ComposeHost: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        SharedComposeRootControllerKt.ComposeRootController()
    }
    func updateUIViewController(_ vc: UIViewController, context: Context) {}
}

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ComposeHost()
                .ignoresSafeArea() // 根据需求选择
        }
    }
}