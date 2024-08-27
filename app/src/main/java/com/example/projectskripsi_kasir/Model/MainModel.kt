package com.example.projectskripsi_kasir.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectskripsi_kasir.*
import com.example.projectskripsi_kasir.API.ApiConfig
import retrofit2.Call

class MainModel: ViewModel() {

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _orderDetails = MutableLiveData<Order?>()
    val orderDetails: LiveData<Order?> = _orderDetails

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _userid = MutableLiveData<String>()
    val userid: LiveData<String> get() = _userid

    private val mToast = MutableLiveData<String>()
    val toast: LiveData<String> get() = mToast

    private val _userOrders = MutableLiveData<List<Order>>()
    val userOrders: LiveData<List<Order>> get() = _userOrders

    fun registerUser(username: String, email: String, password: String) { //done
        val apiService = ApiConfig.getApiService()
        val user = Register(username, email, password)
        val registerUserRequest = apiService.register(user)

        registerUserRequest.enqueue(object : retrofit2.Callback<ApiResponse> {
            override fun onResponse(
                call: retrofit2.Call<ApiResponse>,
                response: retrofit2.Response<ApiResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        _isSuccess.value = true
                    } ?: run {
                        _isSuccess.value = false
                    }
                    Log.d("MainModel", "Registrasi berhasil: ${response.body()}")
                } else {
                    mToast.value = response.message()
                    Log.e("Error:", "$response")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Error: ", "${t.message}")
            }
        })
    }

    fun loginUser(email: String, password: String) {
        val apiService = ApiConfig.getApiService()
        val user = Login(email, password)
        val loginUserRequest = apiService.login(user)

        loginUserRequest.enqueue(object : retrofit2.Callback<LoginRespone> {
            override fun onResponse(
                call: retrofit2.Call<LoginRespone>,
                response: retrofit2.Response<LoginRespone>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        _username.value = it.username
                        _userid.value = it.user_id
                        _isSuccess.value = true
                    } ?: run {
                        _isSuccess.value = false
                        mToast.value = "Akun tidak terdaftar, silahkan lakukan registrasi."
                    }
                    Log.d("MainModel", "Login berhasil: ${response.body()}")
                } else {
                    mToast.value = response.message()
                    Log.e("Error:", "$response")
                }
            }

            override fun onFailure(call: Call<LoginRespone>, t: Throwable) {
                Log.e("Error: ", "${t.message}")
            }
        })
    }

    fun getUserOrders(userId: String) {
        val apiService = ApiConfig.getApiService()
        val call = apiService.getUserOrders(userId)
        call.enqueue(object : retrofit2.Callback<OrderResponse> {
            override fun onResponse(call: retrofit2.Call<OrderResponse>, response: retrofit2.Response<OrderResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        _userOrders.value = it.order
                        _isSuccess.value = true
                    } ?: run {
                        _isSuccess.value = false
                        _error.value = "Response body is null"
                    }
                } else {
                    mToast.value = response.message()
                    _isSuccess.value = false
                    Log.e("Error:", "$response")
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                _isSuccess.value = false
                _error.value = t.message
            }
        })
    }
}