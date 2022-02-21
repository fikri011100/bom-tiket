package bncc.net.bom.api

import bncc.net.bom.model.MovieResponse
import bncc.net.bom.model.TrailerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIClient {

    //?title=a&title_type=tv_movie&genres=action&sort=alpha,asc
    @GET("AdvancedSearch/k_avns8u1u")
    fun getMovies(@Query("title") title : String?,@Query("user_rating") user_rating: String?, @Query("title_type") title_type : String, @Query("genres") genres : String?, @Query("sort") sort : String?) : Call<MovieResponse>
    @GET("YouTubeTrailer/k_avns8u1u/{id}")
    fun getTrailer(@Path("id") id : String) : Call<TrailerResponse>

}