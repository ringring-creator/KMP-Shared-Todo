import ComposeApp

typealias KoinApplication = Koin_coreKoinApplication
typealias Koin = Koin_coreKoin

extension KoinApplication {
    #if DEBUG
        static let shared = companion.start(logEnabled: true)
    #else
        static let shared = companion.start(logEnabled: false)
    #endif
    
    @discardableResult
    static func start() -> KoinApplication {
        shared
    }
}

extension KoinApplication {
    private static let keyPaths: [PartialKeyPath<Koin>] = [
        \.todosScreenModel,
        \.editTodoScreenModel
    ]
    
    static func inject<T>() -> T {
        shared.inject()
    }
    
    func inject<T>() -> T {
        for partialKeyPath in Self.keyPaths {
            guard let keyPath = partialKeyPath as? KeyPath<Koin, T> else { continue }
            return koin[keyPath: keyPath]
        }
        
        fatalError("\(T.self) is not registered with KoinApplication")
    }
}
