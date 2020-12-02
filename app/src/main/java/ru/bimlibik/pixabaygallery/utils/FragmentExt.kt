package ru.bimlibik.pixabaygallery.utils

import androidx.fragment.app.Fragment
import ru.bimlibik.pixabaygallery.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory = InjectorUtils.provideViewModel(this)