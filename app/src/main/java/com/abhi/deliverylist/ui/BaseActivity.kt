package com.abhi.deliverylist.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.abhi.deliverylist.di.Injectable
import com.abhi.deliverylist.utils.BottomDialogListener
import com.abhi.deliverylist.utils.BottomDialogType
import com.abhi.deliverylist.viewModel.BaseViewModel
import dagger.android.AndroidInjection

const val KEY_TITLE = "TITLE"
const val KEY_TYPE_BOTTOM_DIALOG = "TYPE_BOTTOM_DIALOG"

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),Injectable {


    private lateinit var mViewDataBinding: T
    private lateinit var mViewModel: V

    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V


    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView(this,getLayoutId())
        mViewModel = getViewModel()
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

    abstract fun setUpObserver()



    fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    abstract fun showSuccessDialog(message: Int)

    abstract fun showErrorDialog(message: Int)

    /*override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }*/

    /**
     * show Toast message
     * @param text - message
     *
     */
    fun showToast(text: String) {
        val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
        toast.duration = Toast.LENGTH_SHORT
        toast.setText(text)
        toast.setGravity(Gravity.CENTER, 0, 0)
        if (toast.view.windowVisibility == View.VISIBLE)
        else
            toast.show()
    }

    /**
     * open custom dialog with message and heading
     * @param dialogType
     * @param listener
     * */

    fun openDialog(
        @BottomDialogType dialogType: Int, title: String,
        listener: BottomDialogListener
    ) {
        try {
            val bundle = Bundle()
            bundle.putInt(KEY_TYPE_BOTTOM_DIALOG, dialogType)
            bundle.putString(KEY_TITLE, title)
            val dialogFragment = BottomDialogFragment()
            dialogFragment.show(supportFragmentManager, BottomDialogFragment::class.java.simpleName)
            dialogFragment.setOnDialogActionClickListener(listener)
            dialogFragment.isCancelable = false
            dialogFragment.arguments = bundle
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }



}