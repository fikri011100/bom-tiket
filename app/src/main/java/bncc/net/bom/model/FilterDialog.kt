package bncc.net.bom.model

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatDialogFragment
import bncc.net.bom.R

class FilterDialog(var onclick : (sort : String?, genres : String?, rating : String?, price : String?) -> Unit) : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(activity)
        var view = activity?.layoutInflater?.inflate(R.layout.dialog_filter, null)
        var rb_random = view?.findViewById<RadioButton>(R.id.rb_random)
        var rb_az= view?.findViewById<RadioButton>(R.id.rb_az)
        var rb_za = view?.findViewById<RadioButton>(R.id.rb_za)
        var et_price_min = view?.findViewById<EditText>(R.id.et_price_min)
        var et_price_max = view?.findViewById<EditText>(R.id.et_price_max)
        var et_rating_min = view?.findViewById<EditText>(R.id.et_rating_min)
        var et_rating_max = view?.findViewById<EditText>(R.id.et_rating_max)
        var cb = view?.findViewById<CheckBox>(R.id.cb_action)
        var g = ArrayList<CheckBox>()
        view?.findViewById<CheckBox>(R.id.cb_action)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_adventure)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_animation)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_biography)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_comedy)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_crime)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_documentary)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_drama)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_family)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_fantasy)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_film_noir)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_game_show)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_history)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_horror)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_music)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_musical)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_mystery)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_news)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_reality_tv)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_romance)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_sci_fi)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_sport)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_talk_show)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_thriller)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_war)?.let { g.add(it) }
        view?.findViewById<CheckBox>(R.id.cb_western)?.let { g.add(it) }
        builder.setView(view).setTitle("Filters")
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

            })
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
                var sort = if (rb_random?.isChecked == true)  null else if (rb_az?.isChecked == true) "alpha,asc" else if(rb_za?.isChecked == true) "alpha,dsc" else null
                var price_min = et_price_min?.text.toString()
                var price_max = et_price_max?.text.toString()
                var rating_min = et_rating_min?.text.toString()
                var rating_max = et_rating_max?.text.toString()
                var price : String?
                var rating : String?
                var genres : String?
                genres = ""
                rating = ""
                price = ""
                for(e in g){
                    genres += getGenre(e)
                }

                if(!price_max.isNullOrEmpty()&&!price_max.isNullOrEmpty()){
                    price = price_min+","+price_max
                }
                if(!rating_max.isNullOrEmpty()&&!rating_min.isNullOrEmpty()){
                    rating = rating_min+","+rating_max
                }
                if(rating.isEmpty()) rating = null
                if(price.isEmpty()) price = null
                if(genres.isEmpty()) genres = null
                else genres = genres.substring(0, genres.length-1)
                onclick(sort,genres,rating,price)
            })
        return builder.create()
    }

    fun getGenre(cb : CheckBox?) : String {
        return if(cb?.isChecked == true) cb.text.toString()+"," else ""
    }
}