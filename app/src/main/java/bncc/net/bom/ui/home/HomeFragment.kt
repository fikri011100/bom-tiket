package bncc.net.bom.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import bncc.net.bom.ui.movie.MovieDetailActivity
import bncc.net.bom.R
import bncc.net.bom.ui.home.adapter.MovieAdapter
import bncc.net.bom.api.APIClient
import bncc.net.bom.utils.dialog.FilterDialog
import bncc.net.bom.model.Movie
import bncc.net.bom.model.MovieResponse
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class HomeFragment : Fragment() {

    lateinit var adapter : MovieAdapter
    lateinit var retrofit : Retrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieAdapter(Vector(), onclick ={ layout : ConstraintLayout, movie : Movie ->
            layout.setOnClickListener(View.OnClickListener {
                var intent = Intent(it.context, MovieDetailActivity::class.java)
                intent.putExtra("movie",movie)
                startActivity(intent)
            })
        })
        rv_movies.adapter = adapter;
        rv_movies.layoutManager = GridLayoutManager(requireContext(),2);
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
    }

    fun openDialog(){
        var dialog = FilterDialog(onclick = { sort: String?, genres: String?, rating: String?, price: String? ->
            loadData(et_search.text.toString(),sort,genres,rating,price)
        })
        dialog.show(parentFragmentManager,"filter dialog")
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
        call.enqueue(object : Callback<MovieResponse> {
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