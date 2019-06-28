package com.example.customcalendarview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val DAYS_COUNT = 42
    private val calendarList = ArrayList<CalendarModel>()
    private val calendar = Calendar.getInstance()
    private var tahun : Int = -1
    private var monthOfYear : Int = -1
    private var adapter : CalendarAdapter = CalendarAdapter(calendarList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCalendar()

        month.setOnClickListener {
            showMonthYearPicker()
        }
        year.setOnClickListener {
            showMonthYearPicker()
        }

        recyclerView.layoutManager = GridLayoutManager(applicationContext, 7)
        recyclerView.adapter = adapter
    }

    private fun showMonthYearPicker(){
        val dialogFragment  = MonthYearPickerDialogFragment.getInstance(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))
        dialogFragment.show(supportFragmentManager, null)

        dialogFragment.setOnDateSetListener { year, month ->
            tahun = year
            monthOfYear = month
            loadCalendar()
        }
    }

    private fun loadCalendar() {
        //ubah val ke var
        val cells = ArrayList<CalendarModel>()         // inisialisasi variabel untuk setiap tanggal kalender
        if (tahun != -1 && monthOfYear != -1 ){     // pengecekan bila varuiabel tahun dan monthOfYear kosong (-1 hanya pengecoh)
            //ubah obyek kalender ke tahun dan bulan yang diterima
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.YEAR, tahun)
        } else {
            // set variabel tahun dan monthOfYear ke tahun dan bulan sekarang
            tahun = calendar.get(Calendar.YEAR)
            monthOfYear = calendar.get(Calendar.MONTH)
        }
        var sdf = SimpleDateFormat("MMMM,yyyy", Locale("in", "ID"))  // obyek untuk parse bulan dan tahun
        val dateToday = sdf.format(calendar.time).split(",") //format obyek calendar lalu split berdasarkan ,
        month.text = dateToday[0] //settext bulan ke textview month
        year.text = dateToday[1] //settext bulan ke textview year

        //calendarToday
        val calendarCompare : Calendar = Calendar.getInstance() //instansiasi obyek calendar pembanding

        calendarCompare.set(Calendar.MONTH, monthOfYear) //set bulan pada calendar pembanding ke monthOfYear
        calendarCompare.set(Calendar.YEAR, tahun) //set tahun pada calendar pembanding ke tahun


        // memnentukan kapan tanggal dimulai pada bulan
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1

        // pindah calendar ke awal minggu
        calendar.add(Calendar.DAY_OF_MONTH,-monthBeginningCell)

        //obyek untuk parse tanggal
        sdf = SimpleDateFormat("dd-MM-yyyy", Locale("in", "ID"))

        // isi tanggal
        while (cells.size < DAYS_COUNT) {
            if(sdf.format(calendar.time).equals("13-05-2019")){
                cells.add(CalendarModel(
                    calendar.get(Calendar.DATE),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR),
                    calendarCompare,
                    "hijau"
                ))
            }else{
                cells.add(CalendarModel(
                    calendar.get(Calendar.DATE),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR),
                    calendarCompare,
                    null
                ))
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendarList.clear()
        calendarList.addAll(cells)
        adapter.notifyDataSetChanged()
    }

}
