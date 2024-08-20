package com.oguz.coinapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oguz.coinapp.model.CryptoModel
import com.oguz.coinapp.repository.CryptoDownload
import com.oguz.coinapp.service.CrytoAPI
import com.oguz.coinapp.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel(
    private val cryptoDownloadRepository : CryptoDownload
): ViewModel() {


    val cryptoList = MutableLiveData<Resource<List<CryptoModel>>>()
    val cryptoError = MutableLiveData<Resource<Boolean>>()
    val cryptoLoading = MutableLiveData<Resource<Boolean>>()

    private var job : Job? = null


    //TODO: Hata alırsak hatayı burada alabiliyoruz coroutine de
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage}")
        cryptoError.value = Resource.error(throwable.localizedMessage ?:"error",true)
    }

    fun getDataFromAPI(){

        cryptoLoading.value = Resource.loading(true)


        /*
        val BASE_URL = "https://raw.githubusercontent.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CrytoAPI::class.java)

         */

        /*
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {

            Buda kullanılıabilir job yerine tabiki
        }

         */

        //TODO: exceptionHandler- > hata alırsak aktif et
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            //Burda datayı alacağız

            val resource = cryptoDownloadRepository.downloadCryptos()

            //Arkada planda kullanıcya göstereceiz
            withContext(Dispatchers.Main){

                resource.data?.let {
                    cryptoList.value = resource
                    cryptoLoading.value = Resource.loading(false)
                    cryptoError.value = Resource.error("", data = false)
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }



}