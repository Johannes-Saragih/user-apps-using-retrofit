package com.example.projectskripsi_kasir

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi_kasir.Adapter.AdapterRiwayat
import com.example.projectskripsi_kasir.Model.MainModel
import com.google.android.material.appbar.MaterialToolbar
import androidx.lifecycle.Observer
import java.text.NumberFormat
import java.util.*

class RiwayatTransaksi : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterRiwayat
    private lateinit var back: MaterialToolbar

    private val mainModel by viewModels<MainModel>()

    var userId =""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_transaksi)

        recyclerView = findViewById(R.id.recycleriwayat)
        back = findViewById(R.id.topbar)

        userId = intent.getStringExtra("id").toString()

        mainModel.getUserOrders(userId)

        adapter = AdapterRiwayat(emptyList(), this)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        back.setOnClickListener {
            finish()
        }

        mainModel.isSuccess.observe(this, Observer { isSuccess ->
            if (isSuccess){
                mainModel.userOrders.observe(this, Observer { orders ->
                    orders?.let {
                        adapter.updateJadwalList(it)
                    }
                })
            }
        })
    }
}

fun formatToRupiah(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return format.format(amount)
}