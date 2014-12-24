package com.safwan.filmoglass.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper
import com.safwan.filmoglass.R
import com.safwan.filmoglass.models.Film
import groovy.transform.CompileStatic

@CompileStatic
class FilmView extends LinearLayout {

  LinearLayout emptyResult, matchingResult
  TextView title, year, rating
  ImageView poster
  ProgressBar progressBar

  FilmView(Context context) {
    super(context)
    initializeView()
    extractFields()
  }

  private initializeView() {
    def view = LayoutInflater.from(context).inflate(R.layout.live_card, this, false)
    addView(view)
  }

  private extractFields() {
    emptyResult = findViewById(R.id.film_empty_result) as LinearLayout
    matchingResult = findViewById(R.id.film_matching_result) as LinearLayout
    title = findViewById(R.id.film_title) as TextView
    year = findViewById(R.id.film_year) as TextView
    rating = findViewById(R.id.film_rating) as TextView
    poster = findViewById(R.id.film_poster) as ImageView
    progressBar = findViewById(R.id.progress_bar) as ProgressBar
  }

  def populateWith(Film film) {
    progressBar.setVisibility(View.VISIBLE)

    if (film?.isEmpty()) {
      toggleEmptyResult()
    } else {
      toggleMatchingResult()
      title.setText(film.title)
      year.setText(film.year?.toString())
      rating.setText(film.rating?.toString())
      UrlImageViewHelper.setUrlDrawable(poster, film.poster)
    }

    progressBar.setVisibility(View.GONE)
  }

  def toggleEmptyResult() {
    emptyResult.setVisibility(View.VISIBLE)
    matchingResult.setVisibility(View.GONE)
  }

  def toggleMatchingResult() {
    emptyResult.setVisibility(View.GONE)
    matchingResult.setVisibility(View.VISIBLE)
  }

}