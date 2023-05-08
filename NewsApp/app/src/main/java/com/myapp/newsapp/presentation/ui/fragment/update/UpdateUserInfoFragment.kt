package com.myapp.newsapp.presentation.ui.fragment.update

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.myapp.newsapp.databinding.FragmentUpdateUserInfoBinding
import com.myapp.newsapp.util.Constants
import com.myapp.newsapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateUserInfoFragment :BaseFragment<FragmentUpdateUserInfoBinding>(
    FragmentUpdateUserInfoBinding::inflate
){

    private val updateUserInfoViewModel: UpdateUserInfoViewModel by viewModels()

    private val args: UpdateUserInfoFragmentArgs by navArgs()
    override fun handler() {
    }

    override fun intListenner() {
        val currentUser = args.user
        binding.btnUpdate.setOnClickListener {
            Log.d("tag","Click to update")
            var newUserInformation = hashMapOf<String, Any?>()
            currentUser.let {
                newUserInformation = hashMapOf(
                    "/firstName" to binding.etFirstName.text.toString(),
                    "/lastName" to binding.etLastName.text.toString(),
                    "/birth" to binding.etBirth.text.toString()
                )
            }
            currentUser.let { it1 -> updateUserInfoViewModel.updateUserInfo(it1,newUserInformation) }
        }
        binding.tvSkip.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initUI() {
        binding.apply {
            etFirstName.setText(args.user.firstName)
            etLastName.setText(args.user.lastName)
            etBirth.setText(args.user.birth)
        }
    }

    override fun observeViewModel() {
        updateUserInfoViewModel.updateUserInfoState.observe(viewLifecycleOwner){state->
            if(state.isLoading){
                Toast.makeText(requireContext(),Constants.LOADING, Toast.LENGTH_LONG).show()
            }else{
                if(state.isFailed){
                    Toast.makeText(requireContext(),state.message, Toast.LENGTH_LONG).show()
                }else{
                    findNavController().popBackStack()
                }
            }
        }
    }
}