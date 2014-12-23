package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import groovy.transform.CompileStatic
import retrofit.RestAdapter
import retrofit.converter.JacksonConverter
import rx.Observable

@CompileStatic
class OmdbProvider implements RatingProvider {

  @Override
  Observable<Film> getRating(Criteria criteria) {
    new RestAdapter.Builder()
      .setEndpoint('http://www.omdbapi.com')
      .setConverter(new JacksonConverter())
      .build().create(OmdbService.class).getFilm(criteria.title)
  }

  @Override
  Observable<List<Film>> getRatings(Criteria criteria) {
    getRating(criteria).toList()
  }

}