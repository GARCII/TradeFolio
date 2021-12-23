package com.portfolio.tracker.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.portfolio.tracker.R
import com.portfolio.tracker.fragment.DashboardFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DashboardFragment.newInstance())
            .commit()
    }
}