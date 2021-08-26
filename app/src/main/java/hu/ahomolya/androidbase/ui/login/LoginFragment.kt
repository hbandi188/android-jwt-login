package hu.ahomolya.androidbase.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import hu.ahomolya.androidbase.databinding.FragmentLoginBinding
import hu.ahomolya.androidbase.ui.login.impl.LoginViewModelImpl
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    @Inject
    lateinit var viewModel: LoginViewModelImpl

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLoginBinding.inflate(inflater, container, false).also { binding ->
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
    }.root
}