package com.redeyesncode.pay2kart.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.disklrucache.DiskLruCache
import com.redeyesncode.quickpe.R
import java.lang.Exception

open class BaseFragment:Fragment()   {

    lateinit var fragmentContext: Context
    private var loadingDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(R.layout.layout_loading_dialog)
        loadingDialog = builder.create()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fragmentContext = context
    }

    fun showLoadingDialog(){
        if(!loadingDialog?.isShowing!!){
            loadingDialog?.show()
        }

    }

    fun dismissLoadingDialog(){
        try {

            if(loadingDialog!!.isShowing){
                loadingDialog?.dismiss()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        disableScreenShot()


    }




    fun showToast(message:String){

        Toast.makeText(fragmentContext,message, Toast.LENGTH_SHORT).show()


    }

    fun showMessageDialog(message:String,title:String){
        val builder = AlertDialog.Builder(fragmentContext)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
        }

        val mDialog = builder.create()
        if(!mDialog.isShowing){
            mDialog.show()

        }

    }

    fun showBackPressMessageDialog(message: String,title: String){

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
            requireActivity().finish()
        }

        val mDialog = builder.create()
        if(!mDialog.isShowing){
            mDialog.show()

        }
    }

    fun showTopSnackBar(message: String,view:View){
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        val layoutParams = snackbarView.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.BOTTOM
//        layoutParams.gravity = Gravity.TOP
        snackbarView.layoutParams = layoutParams
        val snackbarText = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackbarText.maxLines = 5 // set the maximum number of lines to be displayed

        snackbar.show()

    }
    fun disableScreenShot(){
        // Disable screenshot and screen recording
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}