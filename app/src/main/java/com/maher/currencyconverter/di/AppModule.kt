package com.maher.currencyconverter.di

import com.maher.currencyconverter.common.DispatcherProvider
import com.maher.currencyconverter.data.network.CurrencyApi
import com.maher.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.maher.currencyconverter.domain.repository.CurrencyRepository
import com.maher.currencyconverter.domain.useCases.CompareTwoCurrencies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.currencyscoop.com"
    private const val OK_HTTP_TIMEOUT = 60L

    @Singleton
    @Provides
    fun provideCurrencyApi(client: OkHttpClient): CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository =
        CurrencyRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideCompareTwoCurrencies(repository: CurrencyRepository): CompareTwoCurrencies =
        CompareTwoCurrencies(repository)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}
