package org.deafsapps.android.favquotes.base

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This class implements an [Application] subclass instance which serves as entry point to the app.
 * General tool configurations such as 'LeakCanary' for memory leaks, and 'Dagger' for dependency
 * inversion are initialized here.
 *
 * @author Pablo L. Sordo Martínez
 * @since 1.0
 */
@ExperimentalCoroutinesApi
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        /*
         'ApplicationComponent' is created including all data every associated component needs.
         Specifically, 'modules' parameters refer to those which demand external variables (mostly
         'Context' instances).
         */
    }

}
