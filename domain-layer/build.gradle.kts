plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlin)
    id(Plugins.kotlinKapt)
    // add lint feature
    id(Plugins.detekt)
    // add automatic documentation generator feature
    id(Plugins.dokka)
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
    implementation(Libraries.kotlinCoroutinesCore)
    implementation(Libraries.kotlinCoroutinesAndroid)
    // 3rd party libraries
    detekt(Libraries.detektFormatting)
    detekt(Libraries.detektCli)
    api(Libraries.timber)
    api(Libraries.arrowCore)
    api(Libraries.arrowSyntax)
    kapt(Libraries.arrowMeta)
    api(Libraries.retrofit)
    api(Libraries.dagger)
    kapt(Libraries.daggerCompiler)
    // testing dependencies - Unit Test
    testImplementation(Libraries.junit)
    testImplementation(Libraries.mockitoKotlin)
    testImplementation(Libraries.kotlinCoroutinesTest)
}
