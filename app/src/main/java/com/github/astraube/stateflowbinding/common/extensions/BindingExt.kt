package com.github.astraube.stateflowbinding.common.extensions

import android.view.View
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.astraube.stateflowbinding.common.databinding.FragmentViewBindingDelegate


fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) = FragmentViewBindingDelegate(this, viewBindingFactory)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}