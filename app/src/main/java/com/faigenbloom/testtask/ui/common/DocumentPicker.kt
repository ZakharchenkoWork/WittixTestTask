package com.faigenbloom.testtask.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class DocumentPickerContract : ActivityResultContract<DocumentRequest, DocumentResponse?>() {
    private var lastInput: DocumentRequest? = null

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
        lastInput = input
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
        intent?.data?.let {
            val galleryResponse = DocumentResponse(
                reason = lastInput?.reason,
                uri = it,
            )
            lastInput = null
            return galleryResponse
        }
        return DocumentResponse(null, null)
    }
}

data class DocumentRequest(
    val reason: String?,
)

data class DocumentResponse(
    val reason: String?,
    val uri: Uri?,
)
