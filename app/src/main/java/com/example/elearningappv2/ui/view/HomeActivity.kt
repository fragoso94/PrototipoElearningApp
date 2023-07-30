package com.example.elearningappv2.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.ActivityHomeBinding
import com.example.elearningappv2.ui.view.fragments.CourseFragment
import com.example.elearningappv2.ui.view.fragments.HomeFragment
import com.example.elearningappv2.ui.view.fragments.ProfileFragment
import com.example.elearningappv2.ui.viewmodel.IntroductionViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, HomeActivity::class.java)
    }

    lateinit var binding: ActivityHomeBinding
    private val introductionViewModel: IntroductionViewModel by viewModels()

    val homeFragment: Fragment = HomeFragment()
    val courseFragment: Fragment = CourseFragment()
    val profileFragment: Fragment = ProfileFragment()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var navHostFragment: NavHostFragment
    //lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(homeFragment) //lo primero que cargará es el primer fragmento
        initUI()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        introductionViewModel.onLoginSelected()
    }

    private fun initUI(){
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initListener()
        initObservers()
    }

    private fun initListener(){
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    loadFragment(homeFragment)
                }
                R.id.courseFragment -> {
                    loadFragment(courseFragment)
                }
                R.id.profileFragment -> {
                    loadFragment(profileFragment)
                }
            }
            true
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.courseFragment,
                R.id.profileFragment
            )
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.profileFragment) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }

    private fun initObservers() {
        introductionViewModel.navigateToLogin.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                goToLogin()
            }
        })
    }

    private fun goToLogin() {
        startActivity(MainActivity.create(this))
        finish()
    }

}