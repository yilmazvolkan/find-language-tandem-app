package com.yilmazvolkan.languagetandem.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yilmazvolkan.languagetandem.R
import com.yilmazvolkan.languagetandem.ui.fragments.CommunityFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var communityFragment: CommunityFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            startFragment()
        }

        communityFragment?.setOnBackButtonClicked {
            finishAffinity()
        }
    }

    private fun startFragment() {
        communityFragment = CommunityFragment.newInstance()
        if (communityFragment != null && this.isFinishing.not()) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, communityFragment!!)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }
}