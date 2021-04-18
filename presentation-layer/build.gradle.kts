plugins {
    id(Plugins.androidLibrary)

    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.kotlinParcelize)
    // add lint feature
    id(Plugins.detekt)
    // add automatic documentation generator feature
    id(Plugins.dokka)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        named("release").configure {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    sourceSets {
        getByName("main") { java.srcDir("src/main/kotlin") }
        getByName("test") { java.srcDir("src/test/kotlin") }
        getByName("androidTest") { java.srcDir("src/androidTest/kotlin") }
    }
    buildFeatures {
        viewBinding = true
    }
    lintOptions {
        isAbortOnError = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

tasks {
    val dokka by getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
        skipEmptyPackages = true // skip empty packages
        skipDeprecated = true // skip deprecated
        noStdlibLink = true // skip documentation related to kotlin-stdlib
    }
}

dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.0-alpha01")
    implementation(Libraries.appCompat)
    implementation(Libraries.lifecycle)
    implementation(Libraries.viewModelKtx)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.recyclerview)
    implementation(Libraries.cardview)
    implementation(Libraries.swiperefreshlayout)
    // other modules
    implementation(project(":domain-layer"))
    // 3rd party libraries
    detekt(Libraries.detektFormatting)
    detekt(Libraries.detektCli)
    kapt(Libraries.daggerCompiler)
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)
    // testing dependencies - Unit Test
    testImplementation(Libraries.junit)
    testImplementation(Libraries.mockitoKotlin)
    testImplementation(Libraries.kotlinCoroutinesTest)
}
