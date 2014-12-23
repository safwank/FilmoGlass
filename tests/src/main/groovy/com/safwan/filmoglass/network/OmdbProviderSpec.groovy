package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import pl.polidea.robospock.RoboSpecification
import spock.util.concurrent.BlockingVariable

class OmdbProviderSpec extends RoboSpecification {

  final static int FIVE_SECONDS = 5
  OmdbProvider provider

  def setup() {
    provider = new OmdbProvider()
  }

  def 'gets single rating'() {
    given:
    def result = new BlockingVariable<Film>(FIVE_SECONDS)

    when:
    provider.getRating(new Criteria(title: 'boxtrolls'))
            .subscribe { film -> result.set(film) }

    then:
    def film = result.get()
    film.title == 'The Boxtrolls'
    film.year == 2014
  }

  def 'gets multiple ratings'() {
    given:
    def result = new BlockingVariable<List<Film>>(FIVE_SECONDS)

    when:
    provider.getRatings(new Criteria(title: 'boxtrolls'))
            .subscribe { films -> result.set(films) }

    then:
    def film = result.get().first()
    film.title == 'The Boxtrolls'
    film.year == 2014
  }

}