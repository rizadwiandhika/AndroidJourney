package com.mandiri.learnandroid.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mandiri.learnandroid.R
import com.mandiri.learnandroid.databinding.HomeMainActivityBinding
import com.mandiri.learnandroid.helper.SharedPref
import com.mandiri.learnandroid.presentation.home.HomeFragment
import com.mandiri.learnandroid.presentation.message.MessageFragment

class HomeMainActivity : AppCompatActivity() {

    private lateinit var binding: HomeMainActivityBinding
    private lateinit var preferences: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = SharedPref(this)
        binding = HomeMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(
            onNavigationItemSelectedListener
        )
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.navigationHome
        }
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigationHome -> {
                    replaceFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigationSetting -> {
                    replaceFragment(SettingFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigationPromotion -> {
                    replaceFragment(PromotionFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigationMessage -> {
                    replaceFragment(MessageFragment() { f ->
                        replaceFragment(f)
                    })
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigationLogout -> {
                    handleLogout()
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
//			.add(R.id.fragmentContainer, fragment, fragment.javaClass.simpleName)
//			.addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }

    private fun handleLogout() {
        preferences.clearAll()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}