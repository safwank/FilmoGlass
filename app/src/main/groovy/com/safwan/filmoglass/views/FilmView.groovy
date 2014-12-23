package com.safwan.filmoglass.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper
import com.safwan.filmoglass.R
import com.safwan.filmoglass.models.Film
import groovy.transform.CompileStatic

@CompileStatic
class FilmView extends LinearLayout {

  FilmView(Context context) {
    super(context)
    initializeView()
  }

  private initializeView() {
    def view = LayoutInflater.from(context).inflate(R.layout.live_card, this, false)
    addView(view)
  }

  def populateWith(Film film) {
    findViewById(R.id.film_title).asType(TextView).text = film.title
    findViewById(R.id.film_year).asType(TextView).text = film.year?.toString()
    findViewById(R.id.film_rating).asType(TextView).text = film.rating

    def imageView = findViewById(R.id.film_poster).asType(ImageView)
    UrlImageViewHelper.setUrlDrawable(imageView, film.poster)

    findViewById(R.id.progress_bar).setVisibility(View.GONE)
  }

}