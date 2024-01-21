package com.example.shualeduri.presentation.screen.sign_in

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.shualeduri.databinding.FragmentSignInBinding
import com.example.shualeduri.presentation.base.BaseFragment
import com.example.shualeduri.presentation.event.SignInEvent
import com.example.shualeduri.presentation.state.sign_in.SignInState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private val signInViewModel: SignInViewModel by viewModels()

    override fun setUpListeners() {
        binding.btnSignIn.setOnClickListener {
            signInViewModel.onEvent(
                SignInEvent.SignIn(
                    binding.etSignInEmail.text.toString(),
                    binding.etSignInPassword.text.toString()
                )
            )
        }

        binding.tvGoToSignUp.setOnClickListener {
            signInViewModel.onEvent(SignInEvent.GoToSignUpFragmentEvent)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signInViewModel.signInStateFlow.collect {
                    handleState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                signInViewModel.uiEvent.collect {
                    handleUiEvent(it)
                }
            }
        }
    }

    private fun handleUiEvent(event: SignInViewModel.SignInUiEvent) {
        when (event) {
            is SignInViewModel.SignInUiEvent.NavigateToWallpapers -> findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToWallpaperFragment()
            )

            is SignInViewModel.SignInUiEvent.NavigateToSignUp -> findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            )
        }
    }

    private fun handleState(state: SignInState) {
        binding.progressBarSignIn.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE

        with(binding) {
            tvSignInError.visibility = View.VISIBLE
            if (state.errorMessage != "") {
                tvSignInError.text = state.errorMessage
                progressBarSignIn.visibility = View.INVISIBLE
            }
        }
    }
}