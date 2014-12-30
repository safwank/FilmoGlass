package com.safwan.filmoglass.views

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.safwan.filmoglass.R
import com.safwan.filmoglass.models.Film
import org.robolectric.Robolectric
import pl.polidea.robospock.RoboSpecification

class FilmViewSpec extends RoboSpecification {

  LinearLayout emptyResult, matchingResult
  FilmView view
  TextView title, year, rating
  ImageView poster
  ProgressBar progressBar

  def setup() {
    view = new FilmView(Robolectric.application)
    extractFields(view)
  }

  def extractFields(FilmView view) {
    emptyResult = view.findViewById(R.id.film_empty_result) as LinearLayout
    matchingResult = view.findViewById(R.id.film_matching_result) as LinearLayout
    title = view.findViewById(R.id.film_title) as TextView
    year = view.findViewById(R.id.film_year) as TextView
    rating = view.findViewById(R.id.film_rating) as TextView
    poster = view.findViewById(R.id.film_poster) as ImageView
    progressBar = view.findViewById(R.id.progress_bar) as ProgressBar
  }

  def 'populates view with film when film is not empty'() {
    given:
    def film = new Film(title: 'Braveheart', year: 1995,
      rating: 9.99,
      poster: 'http://movieposters.com/braveheart.png')

    when:
    view.populateWith(film)

    then:
    emptyResult.visibility == View.GONE
    matchingResult.visibility == View.VISIBLE

    and:
    title.text == 'Braveheart'
    year.text == '1995'
    rating.text == '9.99'
  }

  def 'does not populate view when film is empty'() {
    given:
    def film = new Film()

    when:
    view.populateWith(film)

    then:
    emptyResult.visibility == View.VISIBLE
    matchingResult.visibility == View.GONE
  }

  def 'shows progress bar'() {
    given:
    progressBar.visibility = View.INVISIBLE

    when:
    view.showProgressBar()

    then:
    progressBar.visibility == View.VISIBLE
  }

  def 'hides progress bar'() {
    given:
    progressBar.visibility == View.VISIBLE

    when:
    view.hideProgressBar()

    then:
    progressBar.visibility == View.GONE
  }

}