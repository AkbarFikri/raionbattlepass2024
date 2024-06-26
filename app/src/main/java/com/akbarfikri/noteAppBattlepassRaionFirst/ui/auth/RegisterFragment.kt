package com.akbarfikri.noteAppBattlepassRaionFirst.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.akbarfikri.noteAppBattlepassRaionFirst.R
import com.akbarfikri.noteAppBattlepassRaionFirst.data.model.user.User
import com.akbarfikri.noteAppBattlepassRaionFirst.databinding.FragmentRegisterBinding
import com.akbarfikri.noteAppBattlepassRaionFirst.utils.NetworkResult
import com.akbarfikri.noteAppBattlepassRaionFirst.utils.launchInLifeCycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register){
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRegisterBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        ).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {

        launchInLifeCycle {
            if (viewModel.authToken.first() != null)
                findNavController().navigate(
                    RegisterFragmentDirections
                        .actionRegisterFragmentToNotesFragment()
                )
        }

        launchInLifeCycle {
            viewModel.userResponse.collectLatest {
                when (it) {
                    is NetworkResult.Success -> {
                        findNavController()
                            .navigate(
                                RegisterFragmentDirections.actionRegisterFragmentToNotesFragment()
                            )
                    }

                    is NetworkResult.Loading -> {
                        progressBar.isVisible = true
                        txtError.isVisible = false
                    }

                    is NetworkResult.Error -> {
                        progressBar.isVisible = false
                        txtError.isVisible = true
                        txtError.text = it.msg
                    }

                    is NetworkResult.Idle -> {
                        progressBar.isVisible = false
                        txtError.isVisible = false
                    }
                }
            }
        }


        btnLogin.setOnClickListener {
            findNavController()
                .navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                )
        }
        btnSignUp.setOnClickListener {
            if (isUserInputValid())
                viewModel.registerUser(getUserFromInputValue())
        }
    }

    private fun dummySignup() = viewModel.registerUser(
        User(
            name = "dummy",
            nim = "235150201111058",
            password = "password",
        )
    )

    private fun getUserFromInputValue(): User = with(binding) {
        User(
            name = txtUsername.text.toString(),
            nim = txtEmail.text.toString(),
            password = txtPassword.text.toString()
        )
    }

    private fun isUserInputValid(): Boolean = with(binding) {
        val validationResult = viewModel.validateCredential(getUserFromInputValue(), false)
        if (!validationResult.first && validationResult.second != null) {
            txtError.isVisible = true
            txtError.text = validationResult.second
        }
        return@with validationResult.first
    }
}