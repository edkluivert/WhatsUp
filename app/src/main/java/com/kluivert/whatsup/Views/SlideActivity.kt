package com.kluivert.whatsup.Views

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.kluivert.whatsup.R
import com.kluivert.whatsup.fragments.GroupSlideFrag
import com.kluivert.whatsup.fragments.PhotSlideFrag
import com.kluivert.whatsup.fragments.SlidesChatFrag


@Suppress("DEPRECATION")
class SlideActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null
    private var layoutDot: LinearLayoutCompat? = null
    private lateinit  var dot: Array<AppCompatTextView?>
    private val int_items = 3
    lateinit var carddone : CardView


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)


        val isFirstRun =
            getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
                .getBoolean("isFirstRun", true)

        if (!isFirstRun) { //show sign up activity
            startActivity(Intent(this@SlideActivity, PhoneAuth::class.java))

        }


        getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", false).apply()



        carddone = findViewById(R.id.carddone)
        viewPager = findViewById(R.id.viewpager)
        layoutDot = findViewById(R.id.layout_dot)


    carddone.setOnClickListener {
    val intent = Intent(applicationContext, PhoneAuth::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
   }


        viewPager!!.adapter = MyAdapter(supportFragmentManager)
        addIndicator(0)
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                addIndicator(position)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                if (position == 2){
                    carddone.visibility = View.VISIBLE
                }else{
                    carddone.visibility = View.INVISIBLE
                }

            }


            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }


    fun addIndicator(pagePosition : Int){
        dot = arrayOfNulls<AppCompatTextView>(int_items)
        layoutDot!!.removeAllViews()
        for(i in dot.indices){
            dot[i] = AppCompatTextView(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dot[i]!!.text = Html.fromHtml("◉", HtmlCompat.FROM_HTML_MODE_LEGACY)
            }else{
                dot[i]!!.text = Html.fromHtml("◉")
            }
            dot[i]!!.textSize = 35f
            dot[i]!!.setTextColor(Color.parseColor("#B8FAD3"))
            layoutDot!!.addView(dot[i])
        }
        dot[pagePosition]!!.setTextColor(Color.parseColor("#ffffff"))
    }

    private inner class MyAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = SlidesChatFrag()
                1 -> fragment = PhotSlideFrag()
                2 -> fragment = GroupSlideFrag()

            }
            return fragment!!
        }

        override fun getCount(): Int {
            return int_items
        }
    }




}
