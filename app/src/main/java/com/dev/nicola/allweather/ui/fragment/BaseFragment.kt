package com.dev.nicola.allweather.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlin.reflect.KClass

abstract class BaseFragment : Fragment() {

    companion object {
        fun <T : Fragment> newInstance(kClass: KClass<T>, bundle: Bundle? = null): Fragment =
                kClass.java.newInstance().apply { bundle?.let { arguments = it } }
    }

    var disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}