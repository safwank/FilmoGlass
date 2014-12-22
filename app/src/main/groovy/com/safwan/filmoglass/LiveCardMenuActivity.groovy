package com.safwan.filmoglass

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper
import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import com.safwan.filmoglass.network.OmdbProvider
import groovy.transform.CompileStatic
import rx.android.schedulers.AndroidSchedulers

@CompileStatic
class LiveCardMenuActivity extends Activity {

  private static final int SPEECH_REQUEST = 0

  @Override
  void onAttachedToWindow() {
    super.onAttachedToWindow()
    openOptionsMenu()
  }

  @Override
  boolean onCreateOptionsMenu(Menu menu) {
    menuInflater.inflate(R.menu.live_card, menu)
    true
  }

  @Override
  boolean onOptionsItemSelected(MenuItem item) {
    switch (item.itemId) {
      case R.id.action_search_movie:
        displaySpeechRecognizer()
        true
      case R.id.action_stop:
        stopService(new Intent(this, LiveCardService.class))
        true
      default:
        super.onOptionsItemSelected(item)
    }
  }

  private void displaySpeechRecognizer() {
    def intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    startActivityForResult(intent, SPEECH_REQUEST)
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
      def results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
      def spokenFilmTitle = results.first()
      displayMatchFor(spokenFilmTitle)
    }
    super.onActivityResult(requestCode, resultCode, data)
  }

  private void displayMatchFor(String filmTitle) {
    def filmView = layoutInflater.inflate(R.layout.live_card, null)
    new OmdbProvider().getRating(new Criteria(title: filmTitle))
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { Film film ->
        filmView.findViewById(R.id.film_title).asType(TextView).text = film.Title
        filmView.findViewById(R.id.film_year).asType(TextView).text = film.Year.toString()
        filmView.findViewById(R.id.film_rating).asType(TextView).text = film.imdbRating.toString()

        def imageView = filmView.findViewById(R.id.film_poster).asType(ImageView)
        UrlImageViewHelper.setUrlDrawable(imageView, film.Poster)

        setContentView(filmView)
      }
  }

}
