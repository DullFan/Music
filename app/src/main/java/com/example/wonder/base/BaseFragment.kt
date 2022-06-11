package com.example.wonder.base

import android.content.Intent
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    override fun onResume() {
        super.onResume()

    }



    fun <T> startA(clazz: Class<T>) {
        startActivity(Intent(requireContext(), clazz))
    }

}