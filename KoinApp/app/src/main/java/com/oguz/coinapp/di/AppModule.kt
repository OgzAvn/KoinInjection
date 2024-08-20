package com.oguz.coinapp.di

import com.oguz.coinapp.repository.CryptoDownload
import com.oguz.coinapp.repository.CryptoDownloadImplementation
import com.oguz.coinapp.service.CrytoAPI
import com.oguz.coinapp.util.Constants.BASE_URL
import com.oguz.coinapp.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    //TODO:Singleton Scope
    single {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CrytoAPI::class.java)
    }
    //TODO: Retrofit kullanılan heryerde bu bize Inject edilecek


//TODO: CryptoDownload interface sinden bişey inject edilecegi zaman bunu "CryptoDownloadImplementation" inject et diyoruz
    single<CryptoDownload>{
        CryptoDownloadImplementation(get()) //TODO: get() -> daha önce yazdıgım ıcın yukarda benım ıcın bunu alacaktır
    }

    viewModel {
        CryptoViewModel(get())
    }

    /*
    //TODO: factory -> tek bir singleton degil tek bir obje üzerinden gitmiyor
    //  her bir sefer olusturdugunuzda yeni bir nesne oluşturuyor her inject ettigimizde yeni bir nesne oluşturuyor.
    factory {

    }

     */
}