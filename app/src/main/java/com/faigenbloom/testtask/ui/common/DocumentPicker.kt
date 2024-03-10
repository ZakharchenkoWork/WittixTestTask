package com.faigenbloom.testtask.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class DocumentPickerContract : ActivityResultContract<DocumentRequest, DocumentResponse?>() {
    private var lastInputReason: String? = null

    // File types allowed: JPEG, JPG, PNG, PDF, DOC, DOCX.
    private val documentType: Array<String> = arrayOf(
        "image/JPEG",
        "image/JPG",
        "image/PNG",
        "application/pdf",
        "application/doc",
        "application/docx",
    )

    override fun createIntent(context: Context, input: DocumentRequest): Intent {
        lastInputReason = input.reason
        return Intent.createChooser(
            Intent().apply {
                putExtra(
                    Intent.EXTRA_MIME_TYPES,
                    documentType,
                )
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                setType("*/*")
                action = Intent.ACTION_OPEN_DOCUMENT
            },
            "Please select...",
        )
    }

    override fun parseResult(resultCode: Int, intent: Intent?): DocumentResponse {
        val lastInput = lastInputReason
        lastInputReason = null
        return DocumentResponse(
            reason = lastInput,
            uriList = intent?.clipData?.let {
                val result = ArrayList<Uri>()
                for (i in 0 until it.itemCount) {
                    val uri: Uri = it.getItemAt(i).uri
                    result += uri
                }
                result
            } ?: intent?.data?.let {
                listOf(it)
            } ?: emptyList(),
        )
    }
}

data class DocumentRequest(
    val reason: String?,
)

data class DocumentResponse(
    val reason: String?,
    val uriList: List<Uri> = emptyList(),
)
