package com.mwdevp.android.nerdlauncher.Activities

import android.support.v4.app.Fragment
import com.mwdevp.android.nerdlauncher.Fragments.NerdLauncherFragment

class NerdLauncherActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return NerdLauncherFragment.newInstance()
    }


}
