package com.abhi.deliverylist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.abhi.deliverylist.R
import com.abhi.deliverylist.databinding.DialogCommonBinding
import com.abhi.deliverylist.di.Injectable
import com.abhi.deliverylist.utils.BottomDialogListener
import com.abhi.deliverylist.utils.BottomDialogType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection


class BottomDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private var bottomDialogListener: BottomDialogListener? = null

    override fun onClick(v: View?) {
        if (v?.id == R.id.retry) {
            bottomDialogListener?.onDialogClickListener(true)
        } else {
            bottomDialogListener?.onDialogClickListener(false)
        }
        dismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mBinder = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.dialog_common, container, false)
        init(mBinder as DialogCommonBinding)
        return mBinder.root
    }

    private fun init(binding: DialogCommonBinding) {
        val title = arguments?.getString(KEY_TITLE)
        val type = arguments?.getInt(KEY_TYPE_BOTTOM_DIALOG)
        if (type == BottomDialogType.ALL_DATA_FETCHED) {
            binding.retry.visibility = View.GONE
        } else {
            binding.retry.visibility = View.VISIBLE
        }
        binding.tvTitle.text = title
        binding.retry.setOnClickListener(this)
        binding.ok.setOnClickListener(this)
    }

    fun setOnDialogActionClickListener(bottomDialogListener: BottomDialogListener) {
        this.bottomDialogListener = bottomDialogListener
    }
}