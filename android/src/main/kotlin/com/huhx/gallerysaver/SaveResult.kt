package com.huhx.gallerysaver

data class SaveResult(
    val isSuccess: Boolean,
    val filePath: String?,
    val errorMessage: String?
) {
    companion object {
        fun success(filePath: String): SaveResult {
            return SaveResult(isSuccess = true, filePath = filePath, errorMessage = null)
        }

        fun fail(errorMessage: String): SaveResult {
            return SaveResult(isSuccess = false, filePath = null, errorMessage = errorMessage)
        }
    }
}