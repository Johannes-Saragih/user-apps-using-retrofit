package com.example.projectskripsi_kasir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectskripsi_kasir.Adapter.AdapterRiwayatDetail
import com.google.android.material.appbar.MaterialToolbar

class RiwayatDetail : AppCompatActivity() {

    private lateinit var Tanggal: TextView
    private lateinit var idorder: TextView
    private lateinit var totalpayment: TextView
    private lateinit var textmetodepembayaran: TextView

    private lateinit var back: MaterialToolbar

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterRiwayatDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_detail)

        Tanggal = findViewById(R.id.date)
        idorder = findViewById(R.id.IDOrder)
        recyclerView = findViewById(R.id.recycleviewdetail)
        totalpayment = findViewById(R.id.texttotalpembayaran)
        textmetodepembayaran = findViewById(R.id.textmetodepembayaran)
        back = findViewById(R.id.topbar)

        val messageid = intent.getStringExtra("order_id").toString()
        val messagetotal = intent.getDoubleExtra("total_price",0.0)
        val messagetanggal = intent.getStringExtra("time")
        val messagepayment = intent.getStringExtra("payment_method")

        back.setOnClickListener {
            finish()
        }

        adapter = AdapterRiwayatDetail(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        idorder.text = " ID Order : " + messageid
        Tanggal.text = " Tanggal : " + messagetanggal
        totalpayment.text = formatToRupiah(messagetotal)
        textmetodepembayaran.text = messagepayment

        val responses: List<OrderItem>? = intent.getParcelableArrayListExtra("item")
        responses?.let {
            adapter = AdapterRiwayatDetail(it)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        } ?: run {
            Log.e("DetailRiwayat", "OrderItem list is null")
        }

    }
}