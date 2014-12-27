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

  def setup() {
    omdbProvider = Mock(OmdbProvider)
    flixsterProvider = Mock(FlixsterProvider)
    aggregator = new RatingsAggregator(omdbProvider, flixsterProvider)
  }

  def 'returns single matching film with average rating'() {
    given:
    def criteria = new Criteria(title: 'Peaky Blinders')

    and:
    omdbProvider.getRatings(criteria) >> Observable.from([
      new Film(title: 'Peaky Blinders', year: 2013, rating: 8.0)
    ])
    flixsterProvider.getRatings(criteria) >> Observable.from([
      new Film(title: 'Peaky Blinders', year: 2013, rating: 9.0),
      new Film(title: 'The Peaky Blinders II', year: 2014, rating: 9.5)
    ])

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