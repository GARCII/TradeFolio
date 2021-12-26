package com.portfolio.tracker.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.portfolio.tracker.R
import com.portfolio.tracker.fragment.HoldingListFragment

class HoldingListActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(
            activity: Activity) {
            val intent = Intent(activity, HoldingListActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holding_list)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HoldingListFragment.newInstance())
            .commit()
    }
}