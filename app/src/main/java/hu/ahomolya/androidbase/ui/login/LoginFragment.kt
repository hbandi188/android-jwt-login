package hu.ahomolya.androidbase.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import hu.ahomolya.androidbase.databinding.FragmentLoginBinding
import hu.ahomolya.androidbase.ui.login.impl.LoginViewModelImpl
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(), HasDefaultViewModelProviderFactory {
    @Inject
    lateinit var viewModel: LoginViewModelImpl

    @Suppress("UNCHECKED_CAST")
    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            if (modelClass == LoginViewModelImpl::class.java)
                viewModel as T
            else
                error("Wrong viewmodel class needing construction: $modelClass")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLoginBinding.inflate(inflater, container, false).also { binding ->
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = ViewModelProvider(this, viewModelFactory).get(LoginViewModelImpl::class.java)
    }.root
}