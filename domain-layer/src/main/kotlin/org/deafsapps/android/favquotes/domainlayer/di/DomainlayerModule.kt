package org.deafsapps.android.favquotes.domainlayer.di

import dagger.Module
import dagger.Provides
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.feature.splash.SPLASH_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.favquotes.domainlayer.feature.splash.SplashDomainLayerBridge
import org.deafsapps.android.favquotes.domainlayer.feature.splash.SplashDomainLayerBridgeImpl
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.detail.DETAIL_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.detail.DetailDomainLayerBridge
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.detail.DetailDomainLayerBridgeImpl
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.main.MainDomainLayerBridgeImpl
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase.FETCH_QUOTE_BY_ID_UC_TAG
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase.FETCH_QUOTE_LIST_UC_TAG
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase.FetchQuoteByIdUc
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.usecase.FetchQuoteListUc
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

    @Provides
    @Named(MAIN_DOMAIN_BRIDGE_TAG)
    fun provideMainDomainBridge(
        bridge: MainDomainLayerBridgeImpl
    ): @JvmSuppressWildcards MainDomainLayerBridge<QuoteBo> = bridge

    @Provides
    @Named(DETAIL_DOMAIN_BRIDGE_TAG)
    fun provideDetailDomainBridge(
        bridge: DetailDomainLayerBridgeImpl
    ): @JvmSuppressWildcards DetailDomainLayerBridge<QuoteBo> = bridge

}

@Module
object UsecaseModule {

    @Provides
    @Named(FETCH_RANDOM_QUOTE_UC_TAG)
    fun provideFetchRandomQuoteUc(
        usecase: FetchRandomQuoteUc
    ): @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Any, QuoteBo> = usecase

    @Provides
    @Named(FETCH_QUOTE_LIST_UC_TAG)
    fun provideFetchQuoteListQuoteUc(
        usecase: FetchQuoteListUc
    ): @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Any, List<QuoteBo>> =
        usecase

    @Provides
    @Named(FETCH_QUOTE_BY_ID_UC_TAG)
    fun provideFetchQuoteByIdUc(
        usecase: FetchQuoteByIdUc
    ): @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Int, QuoteBo> = usecase

}
