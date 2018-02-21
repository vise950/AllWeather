package com.dev.nicola.allweather.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

inline fun <reified T : ViewModel> AppCompatActivity.viewModel(crossinline f: () -> T): T =
        ViewModelProviders.of(this, factory(f)).get(T::class.java)


inline fun <reified T : ViewModel> FragmentActivity.viewModel(crossinline f: () -> T): T =
        ViewModelProviders.of(this, factory(f)).get(T::class.java)


inline fun <VM : ViewModel> factory(crossinline f: () -> VM) = object : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = f() as T
}