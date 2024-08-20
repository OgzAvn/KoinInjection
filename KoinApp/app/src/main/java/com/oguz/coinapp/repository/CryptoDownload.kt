package com.oguz.coinapp.repository

import com.oguz.coinapp.model.CryptoModel
import com.oguz.coinapp.util.Resource

interface CryptoDownload {

    suspend fun downloadCryptos() : Resource<List<CryptoModel>>

}