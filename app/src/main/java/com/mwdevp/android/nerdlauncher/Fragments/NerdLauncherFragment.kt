package com.mwdevp.android.nerdlauncher.Fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.mwdevp.android.nerdlauncher.R
import java.util.*
import kotlin.Comparator

class NerdLauncherFragment:Fragment() {

    private lateinit var mRecyclerView: RecyclerView


    companion object {
        private const val TAG:String="NerdLauncherFragment"
        fun newInstance():NerdLauncherFragment{
            return NerdLauncherFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val view=inflater.inflate(R.layout.fragment_nerd_launcher,container,false)
        mRecyclerView=view.findViewById(R.id.recycler_view)
        val LinearLayoutManager=LinearLayoutManager(activity)
        mRecyclerView.layoutManager=LinearLayoutManager
        setAdapter()
        mRecyclerView.addItemDecoration(DividerItemDecoration(activity,LinearLayoutManager.orientation))
        return view
    }

    private fun setAdapter(){
        val startIntent= Intent(Intent.ACTION_MAIN)
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pm=activity!!.packageManager
        val activities=pm.queryIntentActivities(startIntent,0)
        activities.sortWith(Comparator { o1, o2 ->
            val pm=activity!!.packageManager
            String.CASE_INSENSITIVE_ORDER.compare(o1!!.loadLabel(pm).toString(),o2!!.loadLabel(pm).toString())
        })
        mRecyclerView.adapter=ActivityAdapter(activities)
        Log.i(TAG,"Znaleziono ${activities.size} aktywnosci")
    }

    private inner class ActivityHolder(view:View):RecyclerView.ViewHolder(view),View.OnClickListener{


        private lateinit var mResolveInfo: ResolveInfo
        private val mView=view
        private val mTextView=view.findViewById<TextView>(R.id.textView_name)
        private val mImageView=view.findViewById<ImageView>(R.id.imageView_icon)

        init {
            mView.setOnClickListener(this)
        }

        fun bindActicity(resolveInfo: ResolveInfo){
            mResolveInfo=resolveInfo
            val pm=activity!!.packageManager
            val appName=mResolveInfo.loadLabel(pm)
            val appIcon=mResolveInfo.loadIcon(pm)
            mTextView.text=appName
            mImageView.setImageDrawable(appIcon)
        }
        override fun onClick(v: View?) {
            val activityInfo=mResolveInfo.activityInfo
            val i=Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName,activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
        }
    }

    private inner class ActivityAdapter(activities:List<ResolveInfo>):RecyclerView.Adapter<ActivityHolder>(){

        private  val mActivities=activities

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
            val view=LayoutInflater.from(activity).inflate(R.layout.simple_list_item,parent,false)
            return ActivityHolder(view)
        }

        override fun getItemCount(): Int {
            return mActivities.size
        }

        override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
            holder.bindActicity(mActivities[position])
        }

    }
}