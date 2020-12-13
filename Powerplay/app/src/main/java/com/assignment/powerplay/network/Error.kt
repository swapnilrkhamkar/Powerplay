package com.assignment.powerplay.network

import android.content.Context
import com.assignment.powerplay.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.IOException
import java.net.SocketException

class Error(private val context: Context, private val error: Throwable) {

    fun showError() {
        when (error) {
            is IOException, is SocketException -> showErrorDialog("Check your internet connection!")
            is NullPointerException, is IllegalArgumentException -> showErrorDialog("Null pointer Exception!")
            is retrofit2.adapter.rxjava2.HttpException -> showErrorDialog("Please try after some time")
        }
    }

    private fun showErrorDialog(title: String) {
        MaterialAlertDialogBuilder(
            context,
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered
        )
            .setTitle(title)
            .setPositiveButton(
                "Okay"
            ) { dialog, _ -> dialog.dismiss() }
            .show()
    }

}