package com.example.projectskripsi_kasir

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class OrderResponse(
    @field:SerializedName("status") val status: String,
    @field:SerializedName("orders") val order: List<Order>,
    @field:SerializedName("message") val message: String
)

@Parcelize
data class Order(
    @field:SerializedName("order_id") val orderId: String,
    @field:SerializedName("time") val time: String,
    @field:SerializedName("total_price") val totalPrice: Double,
    @field:SerializedName("user_id") val userId: String,
    @field:SerializedName("items") val items: List<OrderItem>,
    @field:SerializedName("payment") val payment: Payment?
): Parcelable

@Parcelize
data class OrderItem(
    @field:SerializedName("product_id") val productId: String?,
    @field:SerializedName("product_name") val productName: String?,
    @field:SerializedName("qty") val qty: Int,
    @field:SerializedName("price") val price: Double
):  Parcelable

@Parcelize
data class Payment(
    @field:SerializedName("order_id") val orderId: String,
    @field:SerializedName("payment_method") val paymentMethod: String?,
    @field:SerializedName("payment_status") val paymentStatus: String?
):  Parcelable

data class Register(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class Login(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginRespone(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("user_id") val user_id: String,
    @SerializedName("username") val username: String
)

data class ApiResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

