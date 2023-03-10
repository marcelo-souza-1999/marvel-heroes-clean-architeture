package dependencies

object TestDependencies {

    const val junit = "junit:junit:${Versions.junit}"
    const val androidx_junit = "androidx.test.ext:junit:${Versions.androidx_junit}"
    const val androidx_espresso = "androidx.test.espresso:espresso-contrib:${Versions.androidx_espresso}"
    const val androidx_espresso_core = "androidx.test.espresso:espresso-core:${Versions.androidx_espresso_core}"
    const val androidx_test_runner = "androidx.test:runner:${Versions.androidx_test_runner}"
    const val androidx_test_orchestrator = "androidx.test:orchestrator:${Versions.androidx_test_orchestrator}"
    const val ok_http_mock_test = "com.squareup.okhttp3:mockwebserver:${Versions.test_mock}"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val koin_test = "io.insert-koin:koin-test:${Versions.koin}"
    const val koin_test_junit = "io.insert-koin:koin-test-junit4:${Versions.koin}"
}