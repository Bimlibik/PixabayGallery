package ru.bimlibik.pixabaygallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.bimlibik.pixabaygallery.R
import ru.bimlibik.pixabaygallery.databinding.FragmentPicturesBinding

class PicturesFragment : Fragment() {

    private val viewModel: PicturesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPicturesBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_pictures, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


}