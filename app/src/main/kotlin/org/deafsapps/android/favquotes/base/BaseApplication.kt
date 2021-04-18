package org.deafsapps.android.favquotes.base

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deafsapps.android.favquotes.BuildConfig
import org.deafsapps.android.favquotes.di.ApplicationComponent
import org.deafsapps.android.favquotes.di.DaggerApplicationComponent
import org.deafsapps.android.favquotes.di.UtilsModule
import org.deafsapps.android.favquotes.presentationlayer.di.DetailComponent
import org.deafsapps.android.favquotes.presentationlayer.di.DetailComponentFactoryProvider
import org.deafsapps.android.favquotes.presentationlayer.di.MainComponent
import org.deafsapps.android.favquotes.presentationlayer.di.MainComponentFactoryProvider
import org.deafsapps.android.favquotes.presentationlayer.di.SplashComponent
import org.deafsapps.android.favquotes.presentationlayer.di.SplashComponentFactoryProvider
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * This class implements an [Application] subclass instance which serves as entry point to the app.
 * General tool configurations such as 'LeakCanary' for memory leaks, and 'Dagger' for dependency
 * inversion are initialized here.
 *
 * @since 1.0
 */
@ExperimentalCoroutinesApi
class BaseApplication :
    Application(), SplashComponentFactoryProvider, MainComponentFactoryProvider, DetailComponentFactoryProvider {

    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        /*
         'ApplicationComponent' is created including all data every associated component needs.
         Specifically, 'modules' parameters refer to those which demand external variables (mostly
         'Context' instances).
         */
        appComponent =
            DaggerApplicationComponent.factory().create(modules = UtilsModule(ctx = this))
    }

    override fun provideSplashComponentFactory(): SplashComponent.Factory =
        appComponent.splashComponentFactory()

    override fun provideMainComponentFactory(): MainComponent.Factory =
        appComponent.mainComponentFactory()

    override fun provideDetailComponentFactory(): DetailComponent.Factory =
        appComponent.detailComponentFactory()

}
