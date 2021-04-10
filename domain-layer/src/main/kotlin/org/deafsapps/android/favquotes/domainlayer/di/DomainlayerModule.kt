package org.deafsapps.android.favquotes.domainlayer.di

import DomainlayerContract
import dagger.Module
import dagger.Provides
import org.deafsapps.android.favquotes.domainlayer.base.BaseDomainLayerBridge
import javax.inject.Named

@Module
object BridgeModule {

    @Provides
    fun provideNoDomainBridge() = BaseDomainLayerBridge.None

//    @Provides
//    @Named(MAIN_DOMAIN_BRIDGE_TAG)
//    fun provideMainDomainBridge(bridge: MainDomainLayerBridgeImpl): @JvmSuppressWildcards MainDomainLayerBridge<DataRepoBoWrapper> =
//        bridge

//    @Provides
//    @Named(DETAIL_DOMAIN_BRIDGE_TAG)
//    fun provideDetailDomainBridge(bridge: DetailDomainLayerBridgeImpl): @JvmSuppressWildcards DetailDomainLayerBridge<DataRepoBo> =
//        bridge

}

@Module
object UsecaseModule {

//    @Provides
//    @Named(FETCH_DATA_REPOSITORIES_UC_TAG)
//    fun provideFetchDataRepositoriesUc(usecase: FetchDataRepositoriesUc): @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Any, DataRepoBoWrapper> =
//        usecase

//    @Provides
//    @Named(FETCH_DATA_REPOSITORY_DETAIL_UC_TAG)
//    fun providefetchDataRepositoryDetailUc(usecase: FetchDataRepositoryDetailUc): @JvmSuppressWildcards DomainlayerContract.Presentationlayer.UseCase<Long, DataRepoBo> =
//        usecase

}
