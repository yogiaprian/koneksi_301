@file:Suppress("DEPRECATION")
package com.example.koneksi301

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.yogi.koneksi301.ApiEnd
import kotlinx.android.synthetic.main.activity_manage_fakultas.*
import org.json.JSONObject

class ManageFakultas : AppCompatActivity() {
    lateinit var i:Intent
    lateinit var add:Button
    lateinit var edit: Button
    lateinit var delete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_fakultas)
        add= findViewById(R.id.btnCreate)
        edit= findViewById(R.id.btnUpdate)
        delete= findViewById(R.id.btnDelete)
        i = intent
        if (i.hasExtra("editmode")){
            if(i.getStringExtra("editmode").equals("1")){
                onEditMode()
            }
        }
        add.setOnClickListener {
            onCreate()
        }
        edit.setOnClickListener {
            onUpdate()
        }
        delete.setOnClickListener {
            onDelete()
        }
    }
    private fun onEditMode() {
        TODO("not implemented")
        txt_idfak.setText(i.getStringExtra("txt_idfak"))
        txt_kodef.setText(i.getStringExtra("txt_kodef"))
        txt_namaf.setText(i.getStringExtra("txt_namaf"))
        txt_idfak.isEnabled = false
        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE
    }
    private fun onCreate(){
        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan data...")
        loading.show()
        AndroidNetworking.post(ApiEnd.CREATE)
            .addBodyParameter("kode fakultas", txt_kodef.text.toString())
            .addBodyParameter("nama fakultas", txt_namaf.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()
                    if (response?.getString("message")?.contains("succesfully")!!){
                        this@ManageFakultas.finish()
                    }
                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })

    }
    private fun onUpdate(){
        val loading = ProgressDialog(this)
        loading.setMessage("Mengubah data...")
        loading.show()
        AndroidNetworking.post(ApiEnd.UPDATE)
            .addBodyParameter("id_fakultas", txt_idfak.text.toString())
            .addBodyParameter("kode fakultas", txt_kodef.text.toString())
            .addBodyParameter("nama fakultas", txt_namaf.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()
                    if (response?.getString("message")?.contains("succesfully")!!){
                        this@ManageFakultas.finish()
                    }
                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun onDelete (){
        val loading = ProgressDialog(this)
        loading.setMessage("Mengubah data...")
        loading.show()
        AndroidNetworking.post(ApiEnd.DELETE)
            .addBodyParameter("id_fakultas",txt_idfak.text.toString())
            .addBodyParameter("kode fakultas", txt_kodef.text.toString())
            .addBodyParameter("nama fakultas", txt_namaf.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()
                    if (response?.getString("message")?.contains("succesfully")!!){
                        this@ManageFakultas.finish()
                    }
                }
                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
