package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.Criteria
import pl.polidea.robospock.RoboSpecification

class OmdbProviderSpec extends RoboSpecification {

  OmdbProvider provider

  def setup() {
    provider = new OmdbProvider()
  }

  def 'gets single rating'() {
    when:
    def film = provider.getRating(new Criteria(title: 'boxtrolls'))

    then:
    film.title == 'The Boxtrolls'
    film.year == 2014
  }

  def 'gets multiple ratings'() {
    when:
    def films = provider.getRatings(new Criteria(title: 'boxtrolls'))

    then:
    films.first().title == 'The Boxtrolls'
    films.first().year == 2014
  }

}