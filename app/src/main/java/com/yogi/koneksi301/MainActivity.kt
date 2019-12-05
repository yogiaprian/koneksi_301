@file:Suppress("DEPRECATION")
package com.example.koneksi301

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.yogi.koneksi301.ApiEnd
import com.yogi.koneksi301.Fakultas
import com.yogi.koneksi301.RVAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var arrayList = ArrayList<Fakultas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Data Fakultas"

        recycle_view.setHasFixedSize(true)
        recycle_view.layoutManager = LinearLayoutManager(this)

        mfloatingActionButton.setOnClickListener{
            startActivity(Intent(this, ManageFakultas::class.java))
        }
        loadAllFakultas()
    }

    override fun onResume() {
        super.onResume()
        loadAllFakultas()
    }
    private fun loadAllFakultas() {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()
        AndroidNetworking.get(ApiEnd.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    val jsonArray = response?.optJSONArray("result")
                    if (jsonArray?.length() == 0) {
                        loading.dismiss()
                        Toast.makeText(
                            applicationContext, "Fakultas data empty, add the data first", Toast.LENGTH_SHORT
                        ).show()
                    }
                    for (i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray?.optJSONObject(i)
                        arrayList.add(Fakultas(jsonObject.getString("Id_fakultas"),
                            jsonObject.getString("kode_fakultas"),
                            jsonObject.getString("nama_fakultas")
                        )
                        )
                        if(jsonArray?.length()-1 ==i){
                            loading.dismiss()
                            val adapter = RVAdapter(applicationContext,arrayList)
                            adapter.notifyDataSetChanged()
                            recycle_view.adapter = adapter
                        }
                    }
                }



                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }
            }
            )
    }


}
