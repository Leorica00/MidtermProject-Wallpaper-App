package com.example.shualeduri.presentation.screen.wallpaper_details

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.shualeduri.databinding.FragmentWallpaperDetailsBinding
import com.example.shualeduri.databinding.WallpaperSettingDialogBinding
import com.example.shualeduri.presentation.base.BaseFragment
import com.example.shualeduri.presentation.event.WallpaperDetailsEvent
import com.example.shualeduri.presentation.extension.loadImage
import com.example.shualeduri.presentation.extension.showSnackBar
import com.example.shualeduri.presentation.state.wallpaper_details.WallpaperDetailsState
import com.example.shualeduri.presentation.util.WallpaperUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WallpaperDetailsFragment :
    BaseFragment<FragmentWallpaperDetailsBinding>(FragmentWallpaperDetailsBinding::inflate) {

    private val wallpaperDetailsViewModel: WallpaperDetailsViewModel by viewModels()
    private val args: WallpaperDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var wallpaperUtil: WallpaperUtil

    override fun setUp() {
        wallpaperDetailsViewModel.onEvent(WallpaperDetailsEvent.GetData(
                args.imageId
            )
        )
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                wallpaperDetailsViewModel.imageDetailsStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun showWallpaperSettingDialog(imgUrl: String) {
        val dialogBinding = WallpaperSettingDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        val window = dialog.window
        window?.setGravity(Gravity.BOTTOM)

        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = layoutParams

        dialogBinding.btnSetLockScreen.setOnClickListener {
            wallpaperUtil.downloadAndSetWallpaper(imageUrl = imgUrl, setHomeScreen = false, setLockScreen = true)
            dialog.dismiss()
        }

        dialogBinding.btnSetHomeScreen.setOnClickListener {
            wallpaperUtil.downloadAndSetWallpaper(imageUrl = imgUrl, setHomeScreen = true, setLockScreen = false)
            dialog.dismiss()
        }

        dialogBinding.btnSetBoth.setOnClickListener {
            wallpaperUtil.downloadAndSetWallpaper(imageUrl = imgUrl, setHomeScreen = true, setLockScreen = true)
            dialog.dismiss()
        }

        dialogBinding.btnSetAsProfileImage.setOnClickListener {
            wallpaperDetailsViewModel.onEvent(WallpaperDetailsEvent.SetUserImageEvent(imgUrl))
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun handleState(state: WallpaperDetailsState) {
        binding.progressBarDetails.isVisible = state.isLoading

        state.data?.let {
            with(binding) {
                imageViewDetailsImage.loadImage(it.webformatURL)
                tvViewsNumber.text = it.views.toString()
                tvDownloadsNumber.text = it.downloads.toString()
                tvLikesNumber.text = it.likes.toString()
                tvCommentsNumber.text = it.comments.toString()
                container.visibility = View.VISIBLE
                onDownloadClickListener(it.largeImageURL)
            }
        }
        if (state.errorMessage.isNotEmpty())
            binding.root.showSnackBar(state.errorMessage)
    }

    private fun onDownloadClickListener(imgUrl: String) {
        binding.imageButtonDownload.setOnClickListener {
            showWallpaperSettingDialog(imgUrl)
        }
    }
}