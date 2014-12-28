package com.safwan.filmoglass.managers

import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import com.safwan.filmoglass.network.FlixsterProvider
import com.safwan.filmoglass.network.OmdbProvider
import pl.polidea.robospock.RoboSpecification
import rx.Observable
import spock.util.concurrent.BlockingVariable

class RatingsAggregatorSpec extends RoboSpecification {

  final static int ONE_SECOND = 1
  RatingsAggregator aggregator
  OmdbProvider omdbProvider
  FlixsterProvider flixsterProvider
  Criteria criteria

  def setup() {
    omdbProvider = Mock(OmdbProvider)
    flixsterProvider = Mock(FlixsterProvider)
    aggregator = new RatingsAggregator(omdbProvider, flixsterProvider)
    criteria = new Criteria(title: 'Peaky Blinders')
  }

  def 'returns no match when there are no OMDB and Flixster results'() {
    given:
    omdbProvider.getRating(criteria) >> Observable.just(new Film())
    flixsterProvider.getRating(criteria) >> Observable.just(new Film())

    when:
    def result = new BlockingVariable<Film>(ONE_SECOND)
    aggregator.getAverageRating(criteria).subscribe { result.set(it) }

    then:
    def film = result.get()
    film.isEmpty()
  }

  def 'returns OMDB rating when there is an OMDB result but no Flixster result'() {
    given:
    omdbProvider.getRating(criteria) >>
      Observable.just(new Film(title: 'Peaky Blinders', year: 2013, rating: 8.0))
    flixsterProvider.getRating(criteria) >> Observable.just(new Film())

    when:
    def result = new BlockingVariable<Film>(ONE_SECOND)
    aggregator.getAverageRating(criteria).subscribe { result.set(it) }

    then:
    def film = result.get()
    film.title == 'Peaky Blinders'
    film.year == 2013
    film.rating == 8.0
  }

  def 'returns Flixster rating when there is a Flixster result but no OMDB result'() {
    given:
    omdbProvider.getRating(criteria) >> Observable.just(new Film())
    flixsterProvider.getRating(criteria) >>
      Observable.just(new Film(title: 'Peaky Blinders', year: 2013, rating: 9.0))

    when:
    def result = new BlockingVariable<Film>(ONE_SECOND)
    aggregator.getAverageRating(criteria).subscribe { result.set(it) }

    then:
    def film = result.get()
    film.title == 'Peaky Blinders'
    film.year == 2013
    film.rating == 9.0
  }

  def 'returns OMDB rating when there are OMDB and Flixster results but they do not match'() {
    given:
    omdbProvider.getRating(criteria) >>
      Observable.just(new Film(title: 'Peaky Blinders', year: 2013, rating: 9.0))
    flixsterProvider.getRating(criteria) >>
      Observable.just(new Film(title: 'The Peaky Blinders II', year: 2014, rating: 9.5))

    when:
    def result = new BlockingVariable<Film>(ONE_SECOND)
    aggregator.getAverageRating(criteria).subscribe { result.set(it) }

    then:
    def film = result.get()
    film.title == 'Peaky Blinders'
    film.year == 2013
    film.rating == 9.0
  }

  def 'returns average rating when there are matching OMDB and Flixster results'() {
    given:
    omdbProvider.getRating(criteria) >>
      Observable.just(new Film(title: 'Peaky Blinders', year: 2013, rating: 8.0))
    flixsterProvider.getRating(criteria) >>
      Observable.just(new Film(title: 'Peaky Blinders', year: 2013, rating: 9.0))

    when:
    def result = new BlockingVariable<Film>(ONE_SECOND)
    aggregator.getAverageRating(criteria).subscribe { result.set(it) }

    then:
    def film = result.get()
    film.title == 'Peaky Blinders'
    film.year == 2013
    film.rating == 8.5
  }

}