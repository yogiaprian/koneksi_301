package com.yogi.koneksi301

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koneksi301.R
import kotlinx.android.synthetic.main.fakultas_list.view.*
import java.security.AccessControlContext

class RVAdapter(private val context: Context, private val arrayList: ArrayList<Fakultas>) :
RecyclerView.Adapter<RVAdapter.Holder>(), Parcelable {
    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    constructor(parcel: Parcel) : this(
        TODO("context"),
        TODO("arrayList")
    ) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.fakultas_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.label_idfakultas.text = this.arrayList?.get(position)?.id_fakultas.toString()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.view.label_kodefakultas.text = this.arrayList?.get(position)?.kode_fakultas
        holder.view.label_namafakultas.text = "Nama Fakultas : "+arrayList?.get(position)?.nama_fakultas

        val i = Intent(context,ManageFakultas::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.putExtra("editmode","1")
        i.putExtra("txt_idfak", arrayList?.get(position)?.id_fakultas)
        i.putExtra("txt_kodef", arrayList?.get(position)?.kode_fakultas)
        i.putExtra("txt_namaf", arrayList?.get(position)?.nama_fakultas)
        context.startActivity(i)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RVAdapter> {
        override fun createFromParcel(parcel: Parcel): RVAdapter {
            return RVAdapter(parcel)
        }

        override fun newArray(size: Int): Array<RVAdapter?> {
            return arrayOfNulls(size)
        }
    }

}