package com.safwan.filmoglass.models

import pl.polidea.robospock.RoboSpecification
import spock.lang.Unroll

class FilmSpec extends RoboSpecification {

  def 'isEmpty returns true when all fields are empty'() {
    given:
    def film = new Film()

    when:
    def isEmpty = film.isEmpty()

    then:
    isEmpty
  }

  @Unroll
  def 'isEmpty returns false when at least one field has value'() {
    given:
    def film = new Film(title: title, year: year, rating: rating, poster: poster)

    when:
    def isEmpty = film.isEmpty()

    then:
    !isEmpty

    where:
    title       | year | rating | poster
    'Gone Girl' | 0    | 0      | null
    null        | 2014 | 0      | null
    null        | 0    | 8.88   | null
    null        | 0    | 0      | 'http://movieposters.com/gone_girl.png'
    'Gone Girl' | 2014 | 8.88   | 'http://movieposters.com/gone_girl.png'
  }

}