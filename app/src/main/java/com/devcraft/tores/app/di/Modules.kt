package com.devcraft.tores.app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.devcraft.tores.data.repositories.contract.DashboardRepository
import com.devcraft.tores.data.repositories.contract.FinancesRepository
import com.devcraft.tores.data.repositories.contract.TokenRepository
import com.devcraft.tores.data.repositories.contract.UserRepository
import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.impl.DashboardRepositoryImpl
import com.devcraft.tores.data.repositories.impl.net.impl.FinancesRepositoryImpl
import com.devcraft.tores.data.repositories.impl.net.impl.UserRepositoryImpl
import com.devcraft.tores.data.repositories.impl.net.mappers.*
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.DashBoardApi
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.FinancesApi
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.UserApi
import com.devcraft.tores.data.repositories.impl.prefs.impl.TokenRepositoryPrefsImpl
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.ui.auth.AuthViewModel
import com.devcraft.tores.presentation.ui.main.MainViewModel
import com.devcraft.tores.presentation.ui.main.dashboard.DashBoardViewModel
import com.devcraft.tores.presentation.ui.main.finances.FinancesViewModel
import com.devcraft.tores.presentation.ui.main.finances.rankProfits.RankProfitsViewModel
import com.devcraft.tores.presentation.ui.main.finances.mining.MiningViewModel
import com.devcraft.tores.presentation.ui.main.finances.partnersProfits.PartnersProfitsViewModel
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.TopupsAndWithdrawalsViewModel
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.transactionDetails.TransactionDetailsViewModel
import com.devcraft.tores.presentation.ui.main.finances.transfers.TransfersViewModel
import com.devcraft.tores.presentation.ui.main.finances.transfers.transferDetails.TransferDetailsViewModel
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
    viewModel { FinancesViewModel(get()) }
    viewModel { TopupsAndWithdrawalsViewModel(get(), get()) }
    viewModel { MiningViewModel(get(), get()) }
    viewModel { TransfersViewModel(get(), get()) }
    viewModel { PartnersProfitsViewModel(get(), get()) }
    viewModel { RankProfitsViewModel(get(), get()) }
    viewModel { TransactionDetailsViewModel(get(), get(), get()) }
    viewModel { TransferDetailsViewModel(get(), get(), get()) }
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

    fun provideFinancesApi(retrofit: Retrofit): FinancesApi {
        return retrofit.create(FinancesApi::class.java)
    }

    single { provideUserApi(get()) }
    single { provideDashBoardApi(get()) }
    single { provideFinancesApi(get()) }
}

val repositoryModule = module {
    fun provideTokenRepository(sp: SharedPreferences): TokenRepository {
        return TokenRepositoryPrefsImpl(sp)
    }

    fun provideUserRepository(
        userApi: UserApi,
        tokenRepository: TokenRepository,
        logInTokenMapper: LogInTokenMapper,
        getUserMapper: GetUserMapper
    ): UserRepository {
        return UserRepositoryImpl(
            userApi,
            tokenRepository,
            logInTokenMapper,
            getUserMapper
        )
    }

    fun provideDashboardRepository(
        dashBoardApi: DashBoardApi,
        tokenRepository: TokenRepository,
        getDashboardMapper: GetDashboardMapper

    ): DashboardRepository {
        return DashboardRepositoryImpl(dashBoardApi, tokenRepository, getDashboardMapper)
    }

    fun provideFinancesRepository(
        financesApi: FinancesApi,
        tokenRepository: TokenRepository,
        getTopupsAndWithdrawalsMapper: GetTopupsAndWithdrawalsMapper,
        getMiningHistoryMapper: GetMiningHistoryMapper,
        getTransfersHistoryMapper: GetTransfersHistoryMapper,
        getReferralProfitsHistoryMapper: GetReferralProfitsHistoryMapper,
        getFinanceAllInfoToRankProfitsHistoryMapper: GetFinanceAllInfoToRankProfitsHistoryMapper
    ): FinancesRepository {
        return FinancesRepositoryImpl(
            financesApi,
            tokenRepository,
            getTopupsAndWithdrawalsMapper,
            getMiningHistoryMapper,
            getTransfersHistoryMapper,
            getReferralProfitsHistoryMapper,
            getFinanceAllInfoToRankProfitsHistoryMapper
        )
    }

    single { provideTokenRepository(get(named("tokens"))) }
    single { provideUserRepository(get(), get(), get(), get()) }
    single { provideDashboardRepository(get(), get(), get()) }
    single { provideFinancesRepository(get(), get(), get(), get(), get(), get(), get()) }
}

val repositoryMappersModule = module {
    fun provideLogInTokenMapper(): LogInTokenMapper {
        return LogInTokenMapper()
    }

    fun provideGetUserNetMapper(): GetUserMapper {
        return GetUserMapper()
    }

    fun provideGetDashboardNetMapper(): GetDashboardMapper {
        return GetDashboardMapper()
    }

    fun provideGetTopupsAndWithdrawalsMapper(): GetTopupsAndWithdrawalsMapper {
        return GetTopupsAndWithdrawalsMapper()
    }

    fun provideGetMiningHistoryMapper(): GetMiningHistoryMapper {
        return GetMiningHistoryMapper()
    }

    fun provideGetTransfersHistoryMapper(): GetTransfersHistoryMapper {
        return GetTransfersHistoryMapper()
    }

    fun provideGetReferralProfitsHistoryMapper(): GetReferralProfitsHistoryMapper {
        return GetReferralProfitsHistoryMapper()
    }

    fun provideGetFinanceAllInfoToRankProfitsHistoryMapper(): GetFinanceAllInfoToRankProfitsHistoryMapper {
        return GetFinanceAllInfoToRankProfitsHistoryMapper()
    }

    factory { provideLogInTokenMapper() }
    factory { provideGetUserNetMapper() }
    factory { provideGetDashboardNetMapper() }
    factory { provideGetTopupsAndWithdrawalsMapper() }
    factory { provideGetMiningHistoryMapper() }
    factory { provideGetTransfersHistoryMapper() }
    factory { provideGetReferralProfitsHistoryMapper() }
    factory { provideGetFinanceAllInfoToRankProfitsHistoryMapper() }
}

val utilsModules = module {
    single { ConnectivityInfoLiveData(get()) }
}