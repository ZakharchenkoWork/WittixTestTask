package com.faigenbloom.testtask.ui.common

import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Uri.getName(): String {
    return LocalContext.current.contentResolver
        .query(this, null, null, null, null)
        ?.let {
            it.moveToFirst()
            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index >= 0) {
                val name = it.getString(index)
                it.close()
                name
            } else {
                ""
            }
        } ?: ""
}