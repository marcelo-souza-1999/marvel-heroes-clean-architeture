package dependencies

object DevelopmentDependencies {

    const val koin = "io.insert-koin:koin-android:${Versions.koin}"
    const val koin_annotations = "io.insert-koin:koin-annotations:${Versions.koin_annotations}"
    const val koin_compiler = "io.insert-koin:koin-ksp-compiler:${Versions.koin_annotations}"

    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    const val lifecycle_view_model = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_live_data = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"

    const val paging_3 = "androidx.paging:paging-runtime-ktx:${Versions.paging3}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"

    const val datastore_preferences = "androidx.datastore:datastore-preferences:${Versions.data_store}"

    const val ok_http_bom = "com.squareup.okhttp3:okhttp-bom:${Versions.ok_http_bom}"
    const val ok_http = "com.squareup.okhttp3:okhttp"
    const val ok_http_interceptor = "com.squareup.okhttp3:logging-interceptor"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}