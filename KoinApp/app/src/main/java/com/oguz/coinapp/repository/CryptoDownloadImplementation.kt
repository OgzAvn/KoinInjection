package com.oguz.coinapp.repository

import com.oguz.coinapp.model.CryptoModel
import com.oguz.coinapp.service.CrytoAPI
import com.oguz.coinapp.util.Resource

class CryptoDownloadImplementation(
    private val api : CrytoAPI
) :CryptoDownload{

    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {

        return try {
            val response = api.getData()
            if (response.isSuccessful){
                response.body()?.let{
                    return@let Resource.success(it)
                } ?: Resource.error("error",null)
            }else{
                Resource.error("error",null)
            }
        }catch (e:Exception){
            Resource.error("no data",null)
        }


    }


}