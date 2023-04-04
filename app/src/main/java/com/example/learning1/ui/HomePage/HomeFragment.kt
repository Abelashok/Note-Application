package com.example.learning1.ui.HomePage

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.example.learning1.NoteApplication
import com.example.learning1.R
import com.example.learning1.data.Note
import com.example.learning1.ui.AddNotePage.AddNoteFragment
import com.example.learning1.utils.NavEvent
import com.example.learning1.utils.NavType
import com.example.learning1.utils.NoteEvent
import com.example.learning1.utils.OrderType
import com.example.learning11.ui.MainActivity
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.noowenz.customdatetimepicker.CustomDateTimePicker
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    lateinit var homeViewModel: HomeViewModel
    lateinit var recyclerView: RecyclerView
     var dateFormated:String? = null
    var colorValue:String? = null
    //lateinit var navigationBar:BottomNavigationView
//    lateinit var floatingActionButton: FloatingActionButton
    lateinit var addNoteFragment: AddNoteFragment
    var Notes: LiveData<List<Note>>? = null
    lateinit var  adapter1: HomeListAdapter

    lateinit var BottomMenu:Menu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.ascending -> {
                Notes = homeViewModel.onEvent(NoteEvent.Order(OrderType.Ascending))
                noteAdapterObserver()
                true
            }
            R.id.descending -> {
                Notes = homeViewModel.onEvent(NoteEvent.Order(OrderType.Descending))
                noteAdapterObserver()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun noteAdapterObserver() {
        Notes?.observe(viewLifecycleOwner, Observer { note ->
            if(note.size == 0) {
                var emptytext: TextView = requireView().findViewById(R.id.HomeTextView)
                emptytext.visibility = View.VISIBLE
            }
            else {
                var emptytext: TextView = requireView().findViewById(R.id.HomeTextView)
                emptytext.visibility = View.GONE
                note!!.let {
                    Log.e("ABEL!@#$"," " + it)
                    adapter1.setList(it)
                    adapter1.notifyDataSetChanged()
                }
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(com.example.learning1.R.layout.fragment_home, container, false)
        val factory = HomeViewModelFactory(NoteApplication.repository)
        homeViewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
        recyclerView = view.findViewById(com.example.learning1.R.id.recyclerview)
        addNoteFragment = AddNoteFragment()
        if(Notes == null) {
            Notes = homeViewModel.onEvent(NoteEvent.Order(OrderType.Descending))
            Log.e("Notes1123","" + Notes)
            noteAdapterObserver()
        }
        return view;
    }

    override fun onResume() {
        Log.e("on Resume called","asdadsadas")
        super.onResume()
//        val navigationBar = NavEvent.navigation.navigationBar1
//        navigationBar.selectedItemId = R.id.home

//            Notes = homeViewModel.onEvent(NoteEvent.Order(OrderType.Descending))
//            Log.e("onResume","" + Notes)
//            noteAdapterObserver()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter1 = HomeListAdapter(requireActivity(), ArrayList(),
            HomeListAdapter.OnClickDeleteListener { note: Note, view1: View ->
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Mark as Completed")
                builder.setMessage("Do you want to mark this Note as completed ")
                builder.setPositiveButton("Yes") { dialog, which ->
                    var updatedNote = note
                    updatedNote.completed = true
                    homeViewModel.updateNote(updatedNote)
                }

                builder.setNegativeButton("No") { dialog, which ->
                   //
                }

                builder.show()
            },

            HomeListAdapter.OnClickEditListner { note ->
                if (note.completed == false) {
                    val flatDialog = FlatDialog(context)
                    flatDialog.setTitle("Note Details")
                        .setFirstTextField(note.note_name)
                        .setSecondTextField(note.note_description)
                        .setDateText(note.date)
                        .setFirstButtonText("Update")
                        .setSecondButtonText("Delete")
                        .setFirstButtonColor(Color.parseColor("#03ac13"))
                        .setSecondButtonColor(Color.parseColor("#ff0000"))
                        .setBackgroundColor(Color.parseColor("#455A64"))
                        .dateButtonListner {
                            Toast.makeText(context, "You Clicked : confirm", Toast.LENGTH_SHORT)
                                .show()
                            CustomDateTimePicker(
                                this.requireActivity(),
                                object : CustomDateTimePicker.ICustomDateTimeListener {
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
                                        dateFormated =
                                            SimpleDateFormat("dd/MMM/yyyy hh:mm a").format(
                                                dateSelected
                                            )
                                        flatDialog.setDateText(dateFormated)
                                    }

                                    override fun onCancel() {
                                    }
                                }).apply {
                                set24HourFormat(false)//24hr format is off
                                setMaxMinDisplayDate(
                                    minDate = Calendar.getInstance().apply {
                                        add(
                                            Calendar.MINUTE,
                                            5
                                        )
                                    }.timeInMillis,//min date is 5 min after current time
                                    maxDate = Calendar.getInstance().apply {
                                        add(
                                            Calendar.YEAR,
                                            1
                                        )
                                    }.timeInMillis//max date is next 1 year
                                )
                                setMaxMinDisplayedTime(5)//min time is 5 min after current time
                                setDate(Calendar.getInstance())//date and time will show in dialog is current time and date. We can change this according to our need
                                showDialog()
                            }
                        }
                        .colorButtonListner {
                            Toast.makeText(context, "You Clicked : confirm", Toast.LENGTH_SHORT)
                                .show()

                            MaterialColorPickerDialog
                                .Builder(view.context)
                                .setTitle("Pick Theme")
                                .setColorShape(ColorShape.SQAURE)
                                .setColorSwatch(ColorSwatch._300)
                                .setColors(
                                    arrayListOf(
                                        "#006400",
                                        "#ffbe76",
                                        "#ff7979",
                                        "#badc58",
                                        "#583827",
                                        "#7ed6df",
                                        "#e056fd",
                                        "#686de0",
                                        "#30336b",
                                        "#95afc0"
                                    )
                                )// Pass Default Color
                                .setColorListener { color, colorHex ->
                                    colorValue = colorHex
                                }
                                .show()
                        }
                        .withFirstButtonListner {
                            if (colorValue == null) {
                                colorValue = note.color
                            }
                            if (dateFormated == null) {
                                dateFormated = note.date
                            }
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

                                Log.e("NoTE1", " " + note)
                                val note = Note(
                                    id = note.id,
                                    note_name = title,
                                    note_description = description,
                                    color = colorValue!!,
                                    date = dateFormated
                                )
                                Log.e("NoTE1", " " + note)
                                if (note != null) {
                                    homeViewModel.updateNote(note)
                                }
                                flatDialog.dismiss()
                            }


                        }
                        .withSecondButtonListner {
                            homeViewModel.deleteOne(note)
                            flatDialog.dismiss()

                        }
                        .show()
                }
                else{
                    val snackBar = Snackbar.make(view, "Note is already completed",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null)
                    snackBar.setActionTextColor(Color.BLUE)
                    val snackBarView = snackBar.view
                    snackBarView.setBackgroundColor(Color.BLACK)
                    val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                    textView.setTextColor(Color.WHITE)
                    snackBar.show()
                }
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapter1
        }

    }

}