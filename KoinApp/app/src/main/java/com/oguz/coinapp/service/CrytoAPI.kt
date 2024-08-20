package com.oguz.coinapp.service

import com.oguz.coinapp.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CrytoAPI {

    //TODO: https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    //TODO :BASE_URL : https://raw.githubusercontent.com/


        @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
        suspend fun getData(): Response<List<CryptoModel>>

}
