package com.mwdevp.android.nerdlauncher.Activities

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.mwdevp.android.nerdlauncher.R

/**
 * Created by mateusz on 11.02.18.
 */
abstract class SingleFragmentActivity:AppCompatActivity() {
    protected abstract fun createFragment():Fragment

    @LayoutRes
    protected open fun getLayoutResId():Int{
        return R.layout.activity_fragment
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        var fm: FragmentManager =supportFragmentManager
        var fragment: Fragment?=fm?.findFragmentById(R.id.fragment_container)

        if(fragment==null) {
            fragment = createFragment()
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit()
        }
    }
}