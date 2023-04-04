package com.example.learning1.ui.BottomNavigation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.flatdialoglibrary.dialog.FlatDialog
import com.example.learning1.R
import com.example.learning1.data.Note
import com.example.learning1.ui.AddNotePage.AddNoteFragment
import com.example.learning1.ui.HomePage.HomeFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar


class BottomNavigationFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false)

        // Inflate the layout for this fragment
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}