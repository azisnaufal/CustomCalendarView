package com.example.customcalendarview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(private val mData : ArrayList<CalendarModel>) :
    RecyclerView.Adapter<CalendarAdapter.HomeItem>() {

    override fun onBindViewHolder(holder: HomeItem, position: Int) {
        holder.date.text = mData[position].date.toString()

        holder.date.setOnClickListener {
            Toast.makeText(holder.context, dateBuilder(mData[position].date, mData[position].month+1, mData[position].year), Toast.LENGTH_SHORT).show()
        }

        if (mData[position].month == mData[position].calendarCompare.get(Calendar.MONTH) && mData[position].year == mData[position].calendarCompare.get(Calendar.YEAR)){
            holder.date.setTextColor(ContextCompat.getColor(holder.context, R.color.date_true))
        }
        else holder.date.setTextColor(ContextCompat.getColor(holder.context, R.color.date_false))

        if (mData[position].status.equals("hijau")){
            holder.dot.setImageDrawable(ContextCompat.getDrawable(holder.context,R.drawable.dot))
        }

    }

    private fun dateBuilder(tanggal : Int, bulan : Int, tahun : Int) : String{
        val tgl: String = if (tanggal.toString().length == 1){
            "0$tanggal"
        } else {
            ""+tanggal
        }
        val bln: String = if (bulan.toString().length == 1){
            "0$bulan"
        } else {
            ""+bulan
        }
        return "$tgl-$bln-$tahun"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItem {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return HomeItem(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class HomeItem(mView: View) : RecyclerView.ViewHolder(mView) {
        var date : TextView = mView.findViewById(R.id.date)
        var dot : ImageView = mView.findViewById(R.id.dot)
        var context: Context = mView.context

    }

}