package org.deafsapps.android.favquotes.datalayer.di

import android.annotation.SuppressLint
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.deafsapps.android.favquotes.datalayer.BuildConfig
import org.deafsapps.android.favquotes.datalayer.datasource.AndroidDataSource
import org.deafsapps.android.favquotes.datalayer.datasource.ConnectivityDataSource
import org.deafsapps.android.favquotes.datalayer.datasource.ConnectivityDataSource.Companion.CONNECTIVITY_DATA_SOURCE_TAG
import org.deafsapps.android.favquotes.datalayer.datasource.FavQsDataSource
import org.deafsapps.android.favquotes.datalayer.datasource.QuotesDataSource
import org.deafsapps.android.favquotes.datalayer.datasource.QuotesDataSource.Companion.QUOTES_DATA_SOURCE_TAG
import org.deafsapps.android.favquotes.datalayer.repository.Repository
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract
import org.deafsapps.android.favquotes.domainlayer.DomainlayerContract.Datalayer.Companion.QUOTE_DATA_REPOSITORY_TAG
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

private const val TEN = 10L

@Module
object RepositoryModule {

    @Provides
    @Named(QUOTE_DATA_REPOSITORY_TAG)
    fun provideQuoteDataRepository(
        @Named(CONNECTIVITY_DATA_SOURCE_TAG)
        connectivityDs: ConnectivityDataSource,
        @Named(QUOTES_DATA_SOURCE_TAG)
        quotesDs: QuotesDataSource
    ): @JvmSuppressWildcards DomainlayerContract.Datalayer.QuoteDataRepository<QuoteBo> =
        Repository.apply {
            connectivityDataSource = connectivityDs
            quotesDataSource = quotesDs
        }

}

/**
 *
 */
@Module
class DatasourceModule {

    /**
     *
     */
    @Provides
    @Named(CONNECTIVITY_DATA_SOURCE_TAG)
    fun provideConnectivityDataSource(ds: AndroidDataSource): ConnectivityDataSource = ds

    /**
     *
     */
    @Provides
    @Named(QUOTES_DATA_SOURCE_TAG)
    fun provideRepositoryDataSource(ds: FavQsDataSource): QuotesDataSource = ds

    /**
     *
     */
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .client(getUnsafeHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(QuotesDataSource.API_BASE_URL)
            .build()
    }

}

private fun getUnsafeHttpClient(): OkHttpClient {
    // Create a trust manager that does not validate certificate chains
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
    })

    // Install the all-trusting trust manager
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())
    // Create an ssl socket factory with our all-trusting manager
    val sslSocketFactory = sslContext.socketFactory

    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    return OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .addInterceptor(interceptor)
        .connectTimeout(TEN, TimeUnit.SECONDS)
        .readTimeout(TEN, TimeUnit.SECONDS)
        .build()
}

private fun getHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(TEN, TimeUnit.SECONDS)
        .readTimeout(TEN, TimeUnit.SECONDS)
        .build()
}
