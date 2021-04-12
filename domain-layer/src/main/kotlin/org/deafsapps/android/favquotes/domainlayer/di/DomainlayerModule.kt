package org.deafsapps.android.favquotes.domainlayer.di

import dagger.Module
import dagger.Provides
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.feature.splash.SPLASH_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.favquotes.domainlayer.feature.splash.SplashDomainLayerBridge
import org.deafsapps.android.favquotes.domainlayer.feature.splash.SplashDomainLayerBridgeImpl
import org.deafsapps.android.favquotes.domainlayer.usecase.FETCH_RANDOM_QUOTE_UC_TAG
import org.deafsapps.android.favquotes.domainlayer.usecase.FetchRandomQuoteUc
import javax.inject.Named

@Module
object BridgeModule {

    @Provides
    fun provideNoDomainBridge() = BaseDomainLayerBridge.None

    @Provides
    @Named(SPLASH_DOMAIN_BRIDGE_TAG)
    fun provideSplashDomainBridge(
        bridge: SplashDomainLayerBridgeImpl
    ): @JvmSuppressWildcards SplashDomainLayerBridge<QuoteBo> = bridge

//    @Provides
//    @Named(MAIN_DOMAIN_BRIDGE_TAG)
//    fun provideMainDomainBridge(
//    bridge: MainDomainLayerBridgeImpl
//    ): @JvmSuppressWildcards MainDomainLayerBridge<DataRepoBoWrapper> = bridge

//    @Provides
//    @Named(DETAIL_DOMAIN_BRIDGE_TAG)
//    fun provideDetailDomainBridge(
//    bridge: DetailDomainLayerBridgeImpl
//    ): @JvmSuppressWildcards DetailDomainLayerBridge<DataRepoBo> = bridge

}

@Module
object UsecaseModule {

    @Provides
    @Named(FETCH_RANDOM_QUOTE_UC_TAG)
    fun provideFetchRandomQuoteUc(
        usecase: FetchRandomQuoteUc
    ): @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Any, QuoteBo> = usecase

}
