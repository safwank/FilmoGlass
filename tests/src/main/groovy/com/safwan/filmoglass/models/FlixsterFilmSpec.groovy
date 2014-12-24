package com.safwan.filmoglass.models

import pl.polidea.robospock.RoboSpecification
import spock.lang.Unroll

class FlixsterFilmSpec extends RoboSpecification {

  @Unroll
  def 'calculates average rating'() {
    given:
    def film = new FlixsterFilm()
    def ratings = [
      'critics_rating':'Rotten',
      'critics_score':criticsScore,
      'audience_rating':'Upright',
      'audience_score':audienceScore
    ]

    when:
    film.setRatings(ratings)

    then:
    film.rating == averageRating

    where:
    criticsScore | audienceScore | averageRating
    null         | '100'         | 10.0
    '50'         | null          | 5.0
    '50'         | 'N/A'         | 5.0
    'N/A'        | '50'          | 5.0
    'N/A'        | 'N/A'         | 0.0
    null         | null          | 0.0
    '50'         | '100'         | 7.5
  }

  @Unroll
  def 'sets default poster to first poster with value'() {
    given:
    def film = new FlixsterFilm()
    def posters = [
      'thumbnail':thumbnail,
      'profile':profile,
      'detailed':detailed,
      'original':original
    ]

    when:
    film.setPosters(posters)

    then:
    film.poster == poster

    where:
    thumbnail | profile   | detailed   | original   | poster
    null      | null      | null       | null       | null
    'thumb'   | null      | null       | null       | 'thumb'
    null      | 'profile' | null       | null       | 'profile'
    null      | null      | 'detailed' | null       | 'detailed'
    null      | null      | null       | 'original' | 'original'
    'thumb'   | 'profile' | 'detailed' | 'original' | 'thumb'
  }

}