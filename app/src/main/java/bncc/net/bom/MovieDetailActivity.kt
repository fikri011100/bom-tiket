package bncc.net.bom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import bncc.net.bom.adapter.GenreAdapter
import bncc.net.bom.api.APIClient
import bncc.net.bom.model.Movie
import bncc.net.bom.model.TrailerResponse
import bncc.net.bom.ui.booking.BookingTicketActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.intellij.lang.annotations.JdkConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MovieDetailActivity : AppCompatActivity() {
    lateinit var adapter : GenreAdapter
    lateinit var retrofit : Retrofit
    lateinit var movie : Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        var username = intent.extras?.getString("username")
        movie = intent.getSerializableExtra("movie") as Movie
        retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://imdb-api.com/API/").build();

        adapter = GenreAdapter(Vector<String>())
        rv_genres.adapter = adapter
        rv_genres.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)

        ib_back.setOnClickListener {
            finish()
        }

        var call = retrofit.create(APIClient::class.java).getTrailer(movie.id)
        call.enqueue(object : Callback<TrailerResponse> {
            override fun onResponse(
                call: Call<TrailerResponse>,
                response: Response<TrailerResponse>
            ) {
                tv_title.text = movie.title + " " + response.body()?.year
                yt_trailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        response.body()?.videoId?.let { youTubePlayer.cueVideo(it,1f)
                        }
                    }
                })
                adapter.data.clear()
                var genres = movie.genres.split(",")
                adapter.data =  Vector<String>(genres)
                adapter.notifyDataSetChanged()
                if(movie.price==null ||movie.price==0){
                    movie.price = (((Math.random()*100000).toInt()) + 10000)
                }
                tv_desc.text = movie.plot +"\n\nRp"+movie.price
            }

            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }

        })

        btn_booking.setOnClickListener {
            val intent = Intent(this, BookingTicketActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("image", movie.image)
            intent.putExtra("title", movie.title)
            intent.putExtra("price", movie.price.toString())
            startActivity(intent)
        }

    }
}