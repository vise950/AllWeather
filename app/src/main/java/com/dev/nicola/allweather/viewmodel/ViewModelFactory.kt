package com.dev.nicola.allweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T : ViewModel> AppCompatActivity.viewModel(crossinline f: () -> T): T =
        ViewModelProviders.of(this, factory(f)).get(T::class.java)


inline fun <reified T : ViewModel> FragmentActivity.viewModel(crossinline f: () -> T): T =
        ViewModelProviders.of(this, factory(f)).get(T::class.java)


inline fun <VM : ViewModel> factory(crossinline f: () -> VM) = object : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = f() as T

}