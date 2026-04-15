package com.example.zodiac.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zodiac.data.Horoscope
import com.example.zodiac.R

class DetailActivity : AppCompatActivity() {

    lateinit var signImageView: ImageView
    lateinit var nameTextView: TextView
    lateinit var datesTextView: TextView
    lateinit var horoscope: Horoscope
    lateinit var favoriteMenu: MenuItem
    var isFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        signImageView = findViewById(R.id.signImageView)
        nameTextView = findViewById(R.id.nameTextView)
        datesTextView = findViewById(R.id.datesTextView)

        val id = intent.getStringExtra("HOROSCOPE_ID")!!

        horoscope = Horoscope.Companion.getById(id)!!
        nameTextView.setText(horoscope.name)
        datesTextView.setText(horoscope.dates)
        signImageView.setImageResource(horoscope.image)

        supportActionBar?.setTitle(horoscope.name)
        supportActionBar?.setSubtitle(horoscope.dates)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_detail, menu)

        favoriteMenu = menu.findItem(R.id.menu_favorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                isFavorite = !isFavorite
                if (isFavorite) {
                    (horoscope.id)
                } else {
                    ("")
                }
                setFavoriteMenu()
                true
            }
            R.id.menu_share->{
                share()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun setFavoriteMenu(){
        if(isFavorite){
            favoriteMenu.setIcon(R.drawable.ic_favorite_selected)
        }else{
            favoriteMenu.setIcon(R.drawable.ic_favorite)
        }
    }
    fun share() {
        val sendIntent = Intent()
        sendIntent.setAction(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        sendIntent.setType("text/plain")

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}