package com.portfolio.tracker.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.portfolio.tracker.model.ExchangeTypeItem

internal class HoldingsFragmentPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager,
    private val exchangeTypeList: List<ExchangeTypeItem>
) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = exchangeTypeList[position].getFragment()
    override fun getCount(): Int = exchangeTypeList.size
    override fun getPageTitle(position: Int) = exchangeTypeList[position].getName(context)
}