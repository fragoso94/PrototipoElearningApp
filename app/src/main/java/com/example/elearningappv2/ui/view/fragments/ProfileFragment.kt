package com.example.elearningappv2.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.FragmentProfileBinding
import com.example.elearningappv2.domain.model.Course
import com.example.elearningappv2.ui.view.DetailActivity
import com.example.elearningappv2.ui.view.MainActivity
import com.example.elearningappv2.ui.view.UpdateActivity
import com.example.elearningappv2.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private lateinit var contexto: Context
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            contexto = context
        } catch (e: ClassCastException) {
            throw ClassCastException("$context debe implementar ParametrosListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.imgBtnInfo.setOnClickListener {
            profileViewModel.onUpdateProfileSelected()
        }
        binding.imgBtnSingOut.setOnClickListener {
            profileViewModel.signOutFirebase()
        }
        profileViewModel.getUserCredit()
        profileViewModel.creditModel.observe(this@ProfileFragment, Observer {
            binding.tvCreditUser.text = "Credito - \$ $it"
        })
        initObservers(contexto)
    }

    private fun initObservers(context: Context) {
        profileViewModel.navigateToUpdateProfile.observe(this@ProfileFragment, Observer {
            it.getContentIfNotHandled()?.let {
                //Log.d("observer", it.toString())
                goToDetail(context)
            }
        })
        profileViewModel.navigateToLogin.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToLogin(context)
            }
        })
        profileViewModel.responseModel.observe(this@ProfileFragment, Observer {
            if(it){
                profileViewModel.onLoginSelected()
            }
        })
    }

    private fun goToDetail(context: Context) {
        startActivity(UpdateActivity.create(context))
    }

    private fun goToLogin(context: Context) {
        startActivity(MainActivity.create(context))
    }

}