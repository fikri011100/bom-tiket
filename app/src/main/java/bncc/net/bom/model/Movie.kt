package bncc.net.bom.model

import java.io.Serializable

data class Movie(
    val id: String, val title: String, val description: String,
    val plot: String, val runtimeStr: String, val image: String, val imDbRating: String, val genres: String, var price: Int
) : Serializable{

}