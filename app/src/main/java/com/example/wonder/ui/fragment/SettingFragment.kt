package com.example.wonder.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wonder.R
import com.example.wonder.base.BaseFragment
import com.example.wonder.databinding.FragmentPlaylistDetailsBinding
import com.example.wonder.databinding.FragmentSettingBinding
import com.example.wonder.ui.activity.login.LoginActivity
import com.example.wonder.utils.KV
import com.example.wonder.utils.MMKVData

class SettingFragment : BaseFragment() {
    private val viewDataBinding by lazy {
        FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding.root.layoutParams =
            ViewGroup.LayoutParams(
                requireActivity().resources.displayMetrics.widthPixels,
                requireActivity().resources.displayMetrics.heightPixels
            )


        viewDataBinding.settingButton.setOnClickListener {
            KV.remove(MMKVData.COOKIE)
            KV.remove(MMKVData.U_ID)
            activity!!.onBackPressed()
            startA(LoginActivity::class.java)
        }

        return viewDataBinding.root
    }
}