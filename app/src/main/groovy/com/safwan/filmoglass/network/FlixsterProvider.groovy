package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import com.safwan.filmoglass.models.FlixsterFilm
import groovy.transform.CompileStatic
import retrofit.RestAdapter
import retrofit.converter.JacksonConverter
import rx.Observable

@CompileStatic
class FlixsterProvider implements RatingProvider {

  private static final int LIMIT_TO_ONE = 1
  private static final int LIMIT_TO_FIVE = 5
  private static final String FLIXSTER_API_KEY = 'b2x78beenefg6tq3ynr56r4a'

  @Override
  Observable<Film> getRating(Criteria criteria) {
    def films = getFilms(criteria, LIMIT_TO_ONE)
    films.any() ? films.first() : Observable.empty()
  }

  @Override
  Observable<List<Film>> getRatings(Criteria criteria) {
    getFilms(criteria, LIMIT_TO_FIVE)
  }

  private Observable<List<FlixsterFilm>> getFilms(Criteria criteria, int pageLimit) {
    new RestAdapter.Builder()
      .setEndpoint('http://api.rottentomatoes.com/api/public/v1.0')
      .setConverter(new JacksonConverter())
      .build().create(FlixsterService)
      .getFilms(FLIXSTER_API_KEY, pageLimit, criteria.title) //TODO: move API key to local config
      .map { it.films }
  }

}