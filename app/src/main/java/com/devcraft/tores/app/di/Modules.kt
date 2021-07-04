package com.devcraft.tores.app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.devcraft.tores.data.repositories.contract.*
import com.devcraft.tores.data.repositories.impl.net.ApiConstants
import com.devcraft.tores.data.repositories.impl.net.impl.*
import com.devcraft.tores.data.repositories.impl.net.interceptors.ContentTypeInterceptor
import com.devcraft.tores.data.repositories.impl.net.interceptors.TokenInterceptor
import com.devcraft.tores.data.repositories.impl.net.mappers.*
import com.devcraft.tores.data.repositories.impl.net.retrofitApis.*
import com.devcraft.tores.data.repositories.impl.prefs.impl.TokenRepositoryPrefsImpl
import com.devcraft.tores.presentation.common.ConnectivityInfoLiveData
import com.devcraft.tores.presentation.ui.auth.AuthViewModel
import com.devcraft.tores.presentation.ui.main.MainViewModel
import com.devcraft.tores.presentation.ui.main.more.affiliate.AffiliateViewModel
import com.devcraft.tores.presentation.ui.main.more.affiliate.history.AffiliateHistoryViewModel
import com.devcraft.tores.presentation.ui.main.dashboard.DashBoardViewModel
import com.devcraft.tores.presentation.ui.main.dashboard.topupTores.TopupToresViewModel
import com.devcraft.tores.presentation.ui.main.dashboard.transferTo.TransferToViewModel
import com.devcraft.tores.presentation.ui.main.dashboard.withdrawTores.WithdrawToresViewModel
import com.devcraft.tores.presentation.ui.main.finances.FinancesViewModel
import com.devcraft.tores.presentation.ui.main.finances.rankProfits.RankProfitsViewModel
import com.devcraft.tores.presentation.ui.main.finances.mining.MiningViewModel
import com.devcraft.tores.presentation.ui.main.finances.partnersProfits.PartnersProfitsViewModel
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.TopupsAndWithdrawalsViewModel
import com.devcraft.tores.presentation.ui.main.finances.topupsAndWithdrawals.transactionDetails.TransactionDetailsViewModel
import com.devcraft.tores.presentation.ui.main.finances.transfers.TransfersViewModel
import com.devcraft.tores.presentation.ui.main.finances.transfers.transferDetails.TransferDetailsViewModel
import com.devcraft.tores.presentation.ui.main.mining.addToMining.AddToMiningViewModel
import com.devcraft.tores.presentation.ui.main.mining.withdrawFromMining.WithdrawFromMiningViewModel
import com.devcraft.tores.presentation.ui.main.profile.ProfileViewModel
import com.devcraft.tores.presentation.ui.main.profile.changePassword.ChangePasswordViewModel
import com.devcraft.tores.presentation.ui.main.profile.financePassword.FinancePasswordViewModel
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
    viewModel { SplashViewModel(get(), get()) }
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
    viewModel { AffiliateViewModel(get(), get(), get()) }
    viewModel { AffiliateHistoryViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { ChangePasswordViewModel(get(), get()) }
    viewModel { FinancePasswordViewModel(get(), get()) }
    viewModel { com.devcraft.tores.presentation.ui.main.mining.MiningViewModel(get(), get()) }
    viewModel { AddToMiningViewModel(get(), get()) }
    viewModel { WithdrawFromMiningViewModel(get(), get()) }
    viewModel { TopupToresViewModel(get(), get(), get()) }
    viewModel { TransferToViewModel(get(), get(), get()) }
    viewModel { WithdrawToresViewModel(get(), get(), get()) }
}

val netModule = module {
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        contentTypeInterceptor: ContentTypeInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(contentTypeInterceptor)
            .build()
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

    fun provideTokenInterceptor(tokenRepository: TokenRepository): TokenInterceptor {
        return TokenInterceptor(tokenRepository)
    }

    fun provideContentTypeInterceptor(): ContentTypeInterceptor {
        return ContentTypeInterceptor()
    }

    single { provideOkHttpClient(get(), get()) }
    single { provideGsonConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideTokenInterceptor(get()) }
    single { provideContentTypeInterceptor() }
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

    fun provideAffiliateApi(retrofit: Retrofit): AffiliateApi {
        return retrofit.create(AffiliateApi::class.java)
    }

    fun provideRanksApi(retrofit: Retrofit): RanksApi {
        return retrofit.create(RanksApi::class.java)
    }

    fun provideMiningApi(retrofit: Retrofit): MiningApi {
        return retrofit.create(MiningApi::class.java)
    }

    single { provideUserApi(get()) }
    single { provideDashBoardApi(get()) }
    single { provideFinancesApi(get()) }
    single { provideAffiliateApi(get()) }
    single { provideRanksApi(get()) }
    single { provideMiningApi(get()) }
}

val repositoryModule = module {
    fun provideTokenRepository(sp: SharedPreferences): TokenRepository {
        return TokenRepositoryPrefsImpl(sp)
    }

    fun provideUserRepository(
        userApi: UserApi,
        tokenRepository: TokenRepository,
        logInTokenMapper: LogInTokenMapper,
        getUserMapper: GetUserMapper,
        changePasswordResponseMapper: ChangePasswordResponseMapper
    ): UserRepository {
        return UserRepositoryImpl(
            userApi,
            tokenRepository,
            logInTokenMapper,
            getUserMapper,
            changePasswordResponseMapper
        )
    }

    fun provideDashboardRepository(
        dashBoardApi: DashBoardApi,
        getDashboardMapper: GetDashboardMapper

    ): DashboardRepository {
        return DashboardRepositoryImpl(dashBoardApi, getDashboardMapper)
    }

    fun provideFinancesRepository(
        financesApi: FinancesApi,
        getTopupsAndWithdrawalsMapper: GetTopupsAndWithdrawalsMapper,
        getMiningHistoryMapper: GetMiningHistoryMapper,
        getTransfersHistoryMapper: GetTransfersHistoryMapper,
        getReferralProfitsHistoryMapper: GetReferralProfitsHistoryMapper,
        getFinanceAllInfoToRankProfitsHistoryMapper: GetFinanceAllInfoToRankProfitsHistoryMapper,
        getCurrencyRatesMapper: GetCurrencyRatesMapper
    ): FinancesRepository {
        return FinancesRepositoryImpl(
            financesApi,
            getTopupsAndWithdrawalsMapper,
            getMiningHistoryMapper,
            getTransfersHistoryMapper,
            getReferralProfitsHistoryMapper,
            getFinanceAllInfoToRankProfitsHistoryMapper,
            getCurrencyRatesMapper
        )
    }

    fun provideAffiliateRepository(
        affiliateApi: AffiliateApi,
        getAffiliateMapper: GetAffiliateMapper,
        getAffiliateTreeFirstLineMapper: GetAffiliateTreeFirstLineMapper,
        getAffiliateTreeSpecificLineMapper: GetAffiliateTreeSpecificLineMapper

    ): AffiliateRepository {
        return AffiliateRepositoryImpl(
            affiliateApi,
            getAffiliateMapper,
            getAffiliateTreeFirstLineMapper,
            getAffiliateTreeSpecificLineMapper
        )
    }

    fun provideRankRepository(
        ranksApi: RanksApi,
        getRankInfoMapper: GetRankInfoMapper

    ): RankRepository {
        return RankRepositoryImpl(ranksApi, getRankInfoMapper)
    }

    fun provideMiningRepository(
        miningApi: MiningApi,
        getMiningInfoMapper: GetMiningInfoMapper

    ): MiningRepository {
        return MiningRepositoryImpl(miningApi, getMiningInfoMapper)
    }

    single { provideTokenRepository(get(named("tokens"))) }
    single { provideUserRepository(get(), get(), get(), get(), get()) }
    single { provideDashboardRepository(get(), get()) }
    single { provideFinancesRepository(get(), get(), get(), get(), get(), get(), get()) }
    single { provideAffiliateRepository(get(), get(), get(), get()) }
    single { provideRankRepository(get(), get()) }
    single { provideMiningRepository(get(), get()) }
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

    fun provideGetAffiliateMapper(): GetAffiliateMapper {
        return GetAffiliateMapper()
    }

    fun provideGetRankInfoMapper(): GetRankInfoMapper {
        return GetRankInfoMapper()
    }

    fun provideGetAffiliateTreeFirstLineMapper(): GetAffiliateTreeFirstLineMapper {
        return GetAffiliateTreeFirstLineMapper()
    }

    fun provideGetAffiliateTreeSpecificLineMapper(): GetAffiliateTreeSpecificLineMapper {
        return GetAffiliateTreeSpecificLineMapper()
    }

    fun provideChangePasswordResponseMapper(): ChangePasswordResponseMapper {
        return ChangePasswordResponseMapper()
    }

    fun provideGetMiningInfoMapper(): GetMiningInfoMapper {
        return GetMiningInfoMapper()
    }

    fun provideGetCurrencyRatesMapper(): GetCurrencyRatesMapper {
        return GetCurrencyRatesMapper()
    }

    factory { provideLogInTokenMapper() }
    factory { provideGetUserNetMapper() }
    factory { provideGetDashboardNetMapper() }
    factory { provideGetTopupsAndWithdrawalsMapper() }
    factory { provideGetMiningHistoryMapper() }
    factory { provideGetTransfersHistoryMapper() }
    factory { provideGetReferralProfitsHistoryMapper() }
    factory { provideGetFinanceAllInfoToRankProfitsHistoryMapper() }
    factory { provideGetAffiliateMapper() }
    factory { provideGetRankInfoMapper() }
    factory { provideGetAffiliateTreeFirstLineMapper() }
    factory { provideGetAffiliateTreeSpecificLineMapper() }
    factory { provideChangePasswordResponseMapper() }
    factory { provideGetMiningInfoMapper() }
    factory { provideGetCurrencyRatesMapper() }
}

val utilsModules = module {
    single { ConnectivityInfoLiveData(get()) }
}