package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var CurrImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
  private fun loadmeme()
   {
       progressbar.visibility=View.VISIBLE
       // Instantiate the RequestQueue.
       val queue = Volley.newRequestQueue(this)
     val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
       val  jsonobjectrequest = JsonObjectRequest (
           Request.Method.GET, url,null,
           { response ->
            CurrImageUrl=response.getString("url")

               Glide.with(this).load(CurrImageUrl).listener(object :RequestListener<Drawable>
               {
                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressbar.visibility=View.GONE
                       return false
                   }

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressbar.visibility=View.GONE
                       return false
                   }
               }
               ).into(memeImage)
           },
           {
               Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
           })

// Add the request to the RequestQueue.
    queue.add(jsonobjectrequest)
   }
    fun sharememe(view: View) {
     val  intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Checkout This meme $CurrImageUrl")
        val  chooser=Intent.createChooser(intent,"Share This Using....")
        startActivity(chooser)
    }
    fun next(view: View) {
        loadmeme()
    }
}


