package com.takhir.bank_task

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.atomic.DoubleAdder
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

  private val account = DoubleAdder()

  private val count_threads = 100

  private lateinit var textViewAccount: TextView
  private lateinit var buttonStart: Button
  private lateinit var listTransactions: ListView

  private lateinit var adapter: ArrayAdapter<String>

  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    account.add(1000.00)

    textViewAccount = findViewById(R.id.txt_bank_account)
    buttonStart = findViewById(R.id.btn_start)
    listTransactions = findViewById(R.id.list_transactions)

    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)

    textViewAccount.text = account.toString()
    listTransactions.adapter = adapter

    buttonStart.setOnClickListener {
      for (x in 0 until count_threads) {
        addThread()
      }
    }
  }


  @SuppressLint("SetTextI18n")
  private fun addToView(operationValue: String, delay: Long = 0) {
    val r = Runnable {
      textViewAccount.text = account.toString()
      adapter.add(operationValue)
      adapter.notifyDataSetChanged()
    }
    Handler(Looper.getMainLooper()).postDelayed(r, delay)
  }

  private fun addThread() {
    Thread {
      val operationValue = Random.nextDouble(-1000.00, 1000.00)
      account.add(operationValue)
      addToView(operationValue.toString())
    }.start()
  }
}
