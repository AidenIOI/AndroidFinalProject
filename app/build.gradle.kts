plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.aiden.vectorcalculator"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aiden.vectorcalculator"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources.excludes.add("META-INF/androidx.localbroadcastmanager_localbroadcastmanager.version")
        resources.excludes.add("META-INF/androidx.swiperefreshlayout_swiperefreshlayout.version")
        resources.excludes.add("META-INF/androidx.print_print.version")
        resources.excludes.add("META-INF/androidx.customview_customview.version")
        resources.excludes.add("META-INF/androidx.cursoradapter_cursoradapter.version")
        resources.excludes.add("META-INF/androidx.drawerlayout_drawerlayout.version")
        resources.excludes.add("META-INF/androidx.versionedparcelable_versionedparcelable.version")
        resources.excludes.add("META-INF/androidx.interpolator_interpolator.version")
        resources.excludes.add("META-INF/androidx.fragment_fragment.version")
        resources.excludes.add("META-INF/androidx.vectordrawable_vectordrawable.version")
        resources.excludes.add("META-INF/androidx.core_core.version")
        resources.excludes.add("META-INF/androidx.legacy_legacy-support-core-ui.version")
        resources.excludes.add("META-INF/androidx.legacy_legacy-support-core-utils.version")
        resources.excludes.add("META-INF/proguard/androidx-annotations.pro")
        resources.excludes.add("META-INF/androidx.slidingpanelayout_slidingpanelayout.version")
        resources.excludes.add("META-INF/androidx.print_print.version")
        resources.excludes.add("META-INF/androidx.documentfile_documentfile.version")
        resources.excludes.add("META-INF/androidx.versionedparcelable_versionedparcelable.version")
        resources.excludes.add("META-INF/androidx.asynclayoutinflater_asynclayoutinflater.version")
        resources.excludes.add("META-INF/androidx.drawerlayout_drawerlayout.version")
        resources.excludes.add("META-INF/androidx.interpolator_interpolator.version")
        resources.excludes.add("META-INF/androidx.appcompat_appcompat.version")
        resources.excludes.add("META-INF/androidx.swiperefreshlayout_swiperefreshlayout.version")
        resources.excludes.add("META-INF/androidx.loader_loader.version")
        resources.excludes.add("META-INF/androidx.viewpager_viewpager.version")
        resources.excludes.add("META-INF/androidx.coordinatorlayout_coordinatorlayout.version")
        resources.excludes.add("META-INF/androidx.cursoradapter_cursoradapter.version")
        resources.excludes.add("META-INF/androidx.vectordrawable_vectordrawable-animated.version")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.androidmath) {
        exclude(group = "com.android.support", module = "support-compat")
        exclude(group = "com.android.support", module = "versionedparcelable")
        exclude(group = "com.google.guava", module = "guava")
    }
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}