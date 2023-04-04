package com.example.learning1.ui.AddNotePage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.example.learning1.NoteApplication
import com.example.learning1.R
import com.example.learning1.data.Note
import com.example.learning1.ui.HomePage.HomeFragment
import com.example.learning1.utils.NavEvent
import com.example.learning1.utils.WorkManager
import com.example.learning11.ui.MainActivity
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.snackbar.Snackbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.noowenz.customdatetimepicker.CustomDateTimePicker
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class AddNoteFragment : Fragment() {

    lateinit var addNoteViewModel: AddNoteViewModel
    lateinit var homeFragment: HomeFragment
    var value:String = ""
    var dateFormated:String? = null
    var colorValue:String? = null
    lateinit var navigationBar:com.ismaeldivita.chipnavigation.ChipNavigationBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val bundle = this.arguments
//        if (bundle != null) {
//            value1 = bundle.getSerializable("VALUE2") as Note
//        }
        val factory = AddNoteViewModelFactory(NoteApplication.repository)
        addNoteViewModel = ViewModelProvider(requireActivity(), factory)[AddNoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)

        val flatDialog = FlatDialog(context)
        flatDialog.setTitle("Note Details")
            .setFirstTextFieldHint("Title")
            .setSecondTextFieldHint("Description")
            .setFirstButtonText("Create")
            .setSecondButtonText("Cancel")
            .setFirstButtonColor(Color.parseColor("#03ac13"))
            .setSecondButtonColor(Color.parseColor("#ff0000"))
            .setBackgroundColor(Color.parseColor("#455A64"))
            .dateButtonListner {
                Toast.makeText(context, "You Clicked : confirm", Toast.LENGTH_SHORT).show()
                CustomDateTimePicker(this.requireActivity(), object : CustomDateTimePicker.ICustomDateTimeListener {
                    @SuppressLint("BinaryOperationInTimber")
                    override fun onSet(
                        dialog: Dialog,
                        calendarSelected: Calendar,
                        dateSelected: Date,
                        year: Int,
                        monthFullName: String,
                        monthShortName: String,
                        monthNumber: Int,
                        day: Int,
                        weekDayFullName: String,
                        weekDayShortName: String,
                        hour24: Int,
                        hour12: Int,
                        min: Int,
                        sec: Int,
                        AM_PM: String
                    ) {
                         dateFormated = SimpleDateFormat("dd/MMM/yyyy hh:mm a").format(dateSelected)
                        val date = SimpleDateFormat("dd/MMM/yyyy hh:mm a").parse(dateFormated)
                        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm a").format(Date())

                        val currentDate = SimpleDateFormat("dd/M/yyyy hh:mm a").parse(sdf)


                        var timestamp = date.time - currentDate.time

                        Log.e("MILLI"," " +timestamp/60)


                        NavEvent.DelayMilli = timestamp

                        val NotificationRequest = OneTimeWorkRequestBuilder<WorkManager>()
                            .setConstraints(
                                Constraints.Builder()
                                    .setRequiredNetworkType(
                                        NetworkType.NOT_REQUIRED
                                    )
                                    .build()
                            )
                            .build()

                        val workManager = androidx.work.WorkManager.getInstance(context!!)

                        workManager.beginUniqueWork("New Email",
                            ExistingWorkPolicy.KEEP,NotificationRequest)

                            .enqueue()

                        flatDialog.setDateText(dateFormated)
                    }

                    override fun onCancel() {
                    }
                }).apply {
                    set24HourFormat(false)//24hr format is off
                    setMaxMinDisplayDate(
                        minDate = Calendar.getInstance().apply { add(Calendar.MINUTE, 5) }.timeInMillis,//min date is 5 min after current time
                        maxDate = Calendar.getInstance().apply { add(Calendar.YEAR, 1) }.timeInMillis//max date is next 1 year
                    )
                    setMaxMinDisplayedTime(5)//min time is 5 min after current time
                    setDate(Calendar.getInstance())//date and time will show in dialog is current time and date. We can change this according to our need
                    showDialog()
                }
            }
            .colorButtonListner {
                Toast.makeText(context, "You Clicked : confirm", Toast.LENGTH_SHORT).show()

                MaterialColorPickerDialog
                    .Builder(view.context)
                    .setTitle("Pick Theme")
                    .setColorShape(ColorShape.SQAURE)
                    .setColorSwatch(ColorSwatch._300)
                    .setColors(arrayListOf("#006400", "#ffbe76", "#ff7979", "#badc58", "#583827", "#7ed6df", "#e056fd", "#686de0", "#30336b", "#95afc0"))// Pass Default Color
                    .setColorListener { color, colorHex ->
                      colorValue = colorHex
                    }
                    .show()
            }
            .withFirstButtonListner {
                if (colorValue == null && dateFormated == null) {
                    val snackBar = Snackbar.make(view, "Please Choose Date,Time and Color",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null)
                    snackBar.setActionTextColor(Color.BLUE)
                    val snackBarView = snackBar.view
                    snackBarView.setBackgroundColor(Color.BLACK)
                    val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                    textView.setTextColor(Color.WHITE)
                    snackBar.show()

                } else if (colorValue == null) {
                    val snackBar = Snackbar.make(view, "Please Choose Color",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null)
                    snackBar.setActionTextColor(Color.BLUE)
                    val snackBarView = snackBar.view
                    snackBarView.setBackgroundColor(Color.BLACK)
                    val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                    textView.setTextColor(Color.WHITE)
                    snackBar.show()

                } else if (dateFormated == null) {
                    val snackBar = Snackbar.make(view, "Please Choose Date and Time",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null)
                    snackBar.setActionTextColor(Color.BLUE)
                    val snackBarView = snackBar.view
                    snackBarView.setBackgroundColor(Color.BLACK)
                    val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                    textView.setTextColor(Color.WHITE)
                    snackBar.show()

                } else {
                    val title = flatDialog.firstTextField.toString()
                    val description = flatDialog.secondTextField.toString()
                    val note = Note(note_name = title, note_description = description, color = colorValue!!, date = dateFormated)
                    addNoteViewModel.insert(note)
                    flatDialog.dismiss()
                    click(view)
                }

            }
            .withSecondButtonListner {
                flatDialog.dismiss()
                click(view)
            }
            .show()
        return view
    }

   fun click(view: View){

//        view.findViewById<>(R.id.menu)



//       (activity as MainActivity).onCancelClick()
//       navigationBar = requireActivity().findViewById<ChipNavigationBar>(R.id.menu)
//       navigationBar.setItemSelected(2131362019,true,false )

       homeFragment = HomeFragment()
       val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
       transaction.replace(com.example.learning1.R.id.home, homeFragment)
       transaction.addToBackStack(null)
       transaction.commit()
       NavEvent.navigation.navigationBar1.selectedItemId = R.id.home


   }

}
