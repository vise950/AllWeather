package com.dev.nicola.allweather.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.nicola.allweather.R

/**
 * Created by Nicola on 09/02/2017.
 */

class FavouriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

}
