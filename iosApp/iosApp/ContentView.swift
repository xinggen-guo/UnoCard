import SwiftUI
import UIKit
import shared

struct ComposeHost: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

// 你的页面
struct ContentView: View {
    var body: some View {
        ComposeHost()
            .ignoresSafeArea()
    }
}
