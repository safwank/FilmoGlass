package com.safwan.filmoglass

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import com.safwan.filmoglass.managers.RatingsAggregator
import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import com.safwan.filmoglass.views.FilmView
import groovy.transform.CompileStatic
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

@CompileStatic
class LiveCardMenuActivity extends Activity {

  private static final int SPEECH_REQUEST = 0

  private RatingsAggregator aggregator
  private Subscription subscription

  LiveCardMenuActivity() {
    aggregator = new RatingsAggregator()
  }

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
    def filmView = new FilmView(this)
    setContentView(filmView)
    subscription = aggregator.getAverageRating(new Criteria(title: filmTitle))
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { Film film -> filmView.populateWith(film) }
  }

  @Override
  protected void onDestroy() {
    subscription.unsubscribe()
    super.onDestroy();
  }

}
