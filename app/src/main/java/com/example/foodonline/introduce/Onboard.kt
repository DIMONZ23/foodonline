package com.example.foodonline.introduce

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.foodonline.R
import androidx.viewpager.widget.ViewPager
import me.relex.circleindicator.CircleIndicator


class Onboarding : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private var isLastPage = false
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var textSliderAdapter: TextSliderAdapter
    private val mListText1 = listOf("PizamaTThrea", "Pizza", "Mỳ ý")
    private val mListText2 = listOf("Nơi hội tụ tinh hoa Ý", "Chuẩn công thức Ý", "Được đưa đến từ nông trại Ý")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

//        firtLogin()

        viewPager = findViewById(R.id.viewPager) // Initialize viewPager with the correct ID
        circleIndicator = findViewById(R.id.circle_center)

        textSliderAdapter = TextSliderAdapter(this, mListText1, mListText2)
        viewPager.adapter = textSliderAdapter

        circleIndicator.setViewPager(viewPager)
        textSliderAdapter.registerDataSetObserver(circleIndicator.dataSetObserver)


        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Không cần quan tâm đến sự kiện này
            }

            override fun onPageSelected(position: Int) {
                isLastPage = position == textSliderAdapter.count - 1
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Không cần quan tâm đến sự kiện này
            }
        })

        val nextButton = findViewById<ImageView>(R.id.next_bnt)
        nextButton.setOnClickListener {
            if (isLastPage) {
                // Nếu đang ở trang cuối, chuyển đến màn hình OnboardMenu
                val intent = Intent(this@Onboarding, OnboardMenu::class.java)
                startActivity(intent)
            } else {
                // Nếu không phải trang cuối, chuyển đến trang kế tiếp
                val currentItem = viewPager.currentItem
                val nextItem = currentItem + 1

                // Kiểm tra nếu nextItem không vượt quá số trang có sẵn
                if (nextItem < textSliderAdapter.count) {
                    viewPager.currentItem = nextItem
                }
            }
        }

        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstLogin", false)
        editor.apply()


    }
    private fun firtLogin(){
        // Kiểm tra trạng thái đăng nhập
        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val isFirstLogin = sharedPreferences.getBoolean("isFirstLogin", true)

        if (isFirstLogin) {
            // Hiển thị màn hình onboarding lần đầu
            setContentView(R.layout.activity_onboarding)

            viewPager = findViewById(R.id.viewPager)
            circleIndicator = findViewById(R.id.circle_center)

            textSliderAdapter = TextSliderAdapter(this, mListText1, mListText2)
            viewPager.adapter = textSliderAdapter

            circleIndicator.setViewPager(viewPager)
            textSliderAdapter.registerDataSetObserver(circleIndicator.dataSetObserver)
        } else {
            // Người dùng đã đăng nhập từ trước, chuyển đến màn hình OnboardMenu
            val intent = Intent(this, OnboardMenu::class.java)
            startActivity(intent)
            finish() // Đóng màn hình onboarding
        }
    }

    }



