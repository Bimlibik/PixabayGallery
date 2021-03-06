package ru.bimlibik.pixabaygallery.ui.pictures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.navigation.NavigationView
import ru.bimlibik.pixabaygallery.R
import ru.bimlibik.pixabaygallery.WallpaperWorker
import ru.bimlibik.pixabaygallery.databinding.FragmentPicturesBinding
import ru.bimlibik.pixabaygallery.utils.EventObserver
import ru.bimlibik.pixabaygallery.utils.getViewModelFactory
import ru.bimlibik.pixabaygallery.utils.setupSnackbar

class PicturesFragment : Fragment() {

    private val viewModel: PicturesViewModel by viewModels { getViewModelFactory() }

    private lateinit var viewDataBinding: FragmentPicturesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentPicturesBinding.inflate(inflater, container, false)
            .apply { viewModel = this@PicturesFragment.viewModel }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.start()
        setupAdapter()
        setupSnackbar()
        setupNavDrawerListener()
        observeViewModelEvent()
    }

    private fun observeViewModelEvent() {
        viewModel.wallpaperEvent.observe(viewLifecycleOwner, EventObserver { largeImageURL ->
            val workManager = WorkManager.getInstance(requireContext())
            val url = Data.Builder()
                .putString(WallpaperWorker.IMAGE_URL, largeImageURL)
                .build()

            val request = OneTimeWorkRequestBuilder<WallpaperWorker>()
                .setInputData(url)
                .build()

            workManager.enqueue(request)
            workManager.getWorkInfoByIdLiveData(request.id).observe(viewLifecycleOwner) { info ->
                viewModel.showInfo(info.state)
            }
        })
    }

    private fun setupNavDrawerListener() {
        val navDrawer: NavigationView = requireActivity().findViewById(R.id.nav_drawer)
        val drawerLayout: DrawerLayout = requireActivity().findViewById(R.id.drawer_layout)

        navDrawer.setNavigationItemSelectedListener { item ->
            viewModel.searchByCategory(item.title.toString())
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText)
    }

    private fun setupAdapter() {
        viewDataBinding.recycler.adapter = PicturesAdapter(viewModel)
    }


}