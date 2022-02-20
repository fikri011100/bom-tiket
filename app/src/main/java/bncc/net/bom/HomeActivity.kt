package bncc.net.bom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import bncc.net.bom.adapter.MovieAdapter
import bncc.net.bom.api.APIClient
import bncc.net.bom.model.FilterDialog
import bncc.net.bom.model.Movie
import bncc.net.bom.model.MovieResponse
import bncc.net.bom.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var adapter : MovieAdapter
    lateinit var retrofit : Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var username = intent.extras?.getString("username")

        adapter = MovieAdapter(Vector(), onclick ={ layout : ConstraintLayout, movie : Movie ->
            layout.setOnClickListener(View.OnClickListener {
                var intent = Intent(it.context,MovieDetailActivity::class.java)
                intent.putExtra("movie",movie)
                intent.putExtra("username",username)
                startActivity(intent)
            })
        })
        rv_movies.adapter = adapter;
        rv_movies.layoutManager = GridLayoutManager(this,2);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://imdb-api.com/API/").build();
        loadData("",null,null,null,null)

        et_search.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                loadData(et_search.text.toString(),null,null,null,null)
                //kalau user tiap kali ketik maka filter nya hilang ya
            }
        }

        btn_filter.setOnClickListener(View.OnClickListener {
            openDialog()
        })

        tv_view_profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
        }
    }

    fun openDialog(){
        var dialog = FilterDialog(onclick = { sort: String?, genres: String?, rating: String?, price: String? ->
            loadData(et_search.text.toString(),sort,genres,rating,price)
        })
        dialog.show(supportFragmentManager,"filter dialog")
    }

    fun filterPrice( min : Int,  max : Int, results : Vector<Movie>): Vector<Movie> {
        val data = Vector<Movie>()
        for(m in results){
            m.price = (((Math.random()*100000).toInt()) + 10000)
            if(m.price<= max && m.price>=min) data.add(m)
        }
        return data
    }

    fun loadData(query : String?, sort:String?,genres: String?, rating: String?,priceFilter : String?){
        setDataAdapter(Vector<Movie>())
        val api = retrofit.create(APIClient::class.java)
        val call = api.getMovies(query,rating,"tv_movie",genres,sort)
        call.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                response.body()?.let {
                    priceFilter?.let { s ->
                        var filter = s.split(",")
                        var min = filter[0].toInt()
                        var max = filter[1].toInt()
                        it.results = filterPrice(min, max, it.results)
                    }
                    setDataAdapter(it.results)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("ERROR",t.message.toString())
            }

        })


    }

    fun setDataAdapter(newdata : Vector<Movie>){
        adapter.data.clear();
        adapter.data.addAll(newdata)
        adapter.notifyDataSetChanged()
    }
}