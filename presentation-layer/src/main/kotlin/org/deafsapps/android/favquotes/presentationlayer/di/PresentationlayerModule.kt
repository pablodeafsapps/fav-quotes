package org.deafsapps.android.favquotes.presentationlayer.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module(subcomponents = [SplashComponent::class, MainComponent::class, DetailComponent::class])
object PresentationlayerModule

interface SplashComponentFactoryProvider {
    fun provideSplashComponentFactory(): SplashComponent.Factory
}

@ExperimentalCoroutinesApi
@ActivityScope
@Subcomponent(modules = [SplashModule::class])
interface SplashComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SplashComponent
    }

//    fun inject(activity: SplashActivity)

}

@ExperimentalCoroutinesApi
@Module
class SplashModule {

//    @ActivityScope
//    @Provides
//    @Named(SPLASH_VIEW_MODEL_TAG)
//    fun provideSplashViewModel(viewModel: SplashViewModel): @JvmSuppressWildcards BaseMvvmViewModel<BaseDomainLayerBridge.None, SplashState> =
//        viewModel

}

interface MainComponentFactoryProvider {
    fun provideMainComponentFactory(): MainComponent.Factory
}

@ExperimentalCoroutinesApi
@ActivityScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

//    fun inject(activity: MainActivity)

}

@ExperimentalCoroutinesApi
@Module
class MainModule {

//    @ActivityScope
//    @Provides
//    @Named(MAIN_VIEW_MODEL_TAG)
//    fun provideMainViewModel(viewModel: MainViewModel): @JvmSuppressWildcards BaseMvvmViewModel<MainDomainLayerBridge<DataRepoBoWrapper>, MainState> =
//        viewModel

}

interface DetailComponentFactoryProvider {
    fun provideDetailComponentFactory(): DetailComponent.Factory
}

@ExperimentalCoroutinesApi
@ActivityScope
@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailComponent
    }

//    fun inject(activity: DetailActivity)

}

@ExperimentalCoroutinesApi
@Module
class DetailModule {

//    @ActivityScope
//    @Provides
//    @Named(DETAIL_VIEW_MODEL_TAG)
//    fun provideDetailViewModel(viewModel: DetailViewModel): @JvmSuppressWildcards BaseMvvmViewModel<DetailDomainLayerBridge<DataRepoBo>, DetailState> =
//        viewModel

}
