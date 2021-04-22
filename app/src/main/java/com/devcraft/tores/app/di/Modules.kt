package com.devcraft.tores.app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.devcraft.tores.data.repositories.contract.DashboardRepository
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.impl.DashboardRepositoryNetImpl
import com.devcraft.tores.data.repositories.impl.net.impl.UserRepositoryNetImpl
import com.devcraft.tores.data.repositories.impl.net.mappers.GetDashboardNetMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.GetUserNetMapper
import com.devcraft.tores.data.repositories.impl.net.mappers.LogInTokenMapper
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.DashBoardApi
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.UserApi
import com.devcraft.tores.data.repositories.impl.prefs.impl.TokenRepositoryPrefsImpl
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.ui.auth.AuthViewModel
import com.devcraft.tores.presentation.ui.main.MainViewModel
import com.devcraft.tores.presentation.ui.main.dashboard.DashBoardViewModel
import com.devcraft.tores.presentation.ui.splash.SplashViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { AuthViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DashBoardViewModel(get(), get(), get()) }
}

val netModule = module {
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()
        )
    }

    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    single { provideOkHttpClient() }
    single { provideGsonConverterFactory() }
    single { provideRetrofit(get(), get()) }
}

val preferencesModule = module {
    fun provideTokenSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("tokens", Context.MODE_PRIVATE)
    }

    single(named("tokens")) { provideTokenSharedPreferences(get()) }
}

val netApiModule = module {
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    fun provideDashBoardApi(retrofit: Retrofit): DashBoardApi {
        return retrofit.create(DashBoardApi::class.java)
    }

    single { provideUserApi(get()) }
    single { provideDashBoardApi(get()) }
}

val repositoryModule = module {
    fun provideTokenRepository(sp: SharedPreferences): TokenRepository {
        return TokenRepositoryPrefsImpl(sp)
    }

    fun provideUserRepository(
        userApi: UserApi,
        tokenRepository: TokenRepository,
        logInTokenMapper: LogInTokenMapper,
        getUserNetMapper: GetUserNetMapper
    ): UserRepository {
        return UserRepositoryNetImpl(
            userApi,
            tokenRepository,
            logInTokenMapper,
            getUserNetMapper
        )
    }

    fun provideDashboardRepository(
        dashBoardApi: DashBoardApi,
        tokenRepository: TokenRepository,
        getDashboardNetMapper: GetDashboardNetMapper

    ): DashboardRepository {
        return DashboardRepositoryNetImpl(dashBoardApi, tokenRepository, getDashboardNetMapper)
    }

    single { provideTokenRepository(get(named("tokens"))) }
    single { provideUserRepository(get(), get(), get(), get()) }
    single { provideDashboardRepository(get(), get(), get()) }
}

val repositoryMappersModule = module {
    fun provideLogInTokenMapper(): LogInTokenMapper {
        return LogInTokenMapper()
    }

    fun provideGetUserNetMapper(): GetUserNetMapper {
        return GetUserNetMapper()
    }

    fun provideGetDashboardNetMapper(): GetDashboardNetMapper{
        return GetDashboardNetMapper()
    }

    factory { provideLogInTokenMapper() }
    factory { provideGetUserNetMapper() }
    factory { provideGetDashboardNetMapper() }
}

val utilsModules = module {
    single { ConnectivityInfoLiveData(get()) }
}