package com.github.astraube.stateflowbinding.common.databinding

import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.github.astraube.stateflowbinding.data.source.ResultSource
import com.github.astraube.stateflowbinding.data.source.isLoading


@BindingAdapter("goneUnless")
fun TextView.bindGoneUnless(text: String?) {
    this.isVisible = !text.isNullOrBlank()
}

@BindingAdapter("isLoadingTop")
fun ProgressBar.bindLoadingTop(enabled: Boolean) {
    this.isVisible = enabled
    this.isIndeterminate = enabled
}

@BindingAdapter("isLoadingTop")
fun ProgressBar.bindLoadingTopStateFlow(uiState: ResultSource<*>?) {
    this.bindLoadingTop(uiState?.isLoading ?: false)
}