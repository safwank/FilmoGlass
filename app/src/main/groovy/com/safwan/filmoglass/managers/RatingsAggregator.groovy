package com.safwan.filmoglass.managers

import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import com.safwan.filmoglass.network.FlixsterProvider
import com.safwan.filmoglass.network.OmdbProvider
import groovy.transform.CompileStatic
import rx.Observable

@CompileStatic
class RatingsAggregator {

  OmdbProvider omdbProvider
  FlixsterProvider flixsterProvider

  RatingsAggregator() {
    this(new OmdbProvider(), new FlixsterProvider())
  }

  RatingsAggregator(OmdbProvider omdbProvider, FlixsterProvider flixsterProvider) {
    this.omdbProvider = omdbProvider
    this.flixsterProvider = flixsterProvider
  }

  Observable<Film> getAverageRating(Criteria criteria) {
    def omdbMatches = omdbProvider.getRatings(criteria)
    def flixsterMatches = flixsterProvider.getRatings(criteria)
    Observable.merge(omdbMatches, flixsterMatches)
      .reduce { List<Film> omdbFilms, List<Film> flixsterFilms ->
        def omdbFilm = omdbFilms.first()
        if (!omdbFilm) new Film()
        def flixsterFilm = flixsterFilms.find { it.title == omdbFilm.title && it.year == omdbFilm.year }
        new Film(
          title: omdbFilm.title,
          year: omdbFilm.year,
          rating: flixsterFilm ? (omdbFilm.rating + flixsterFilm.rating) / 2 as float : omdbFilm.rating,
          poster: omdbFilm.poster
        )
      }
  }

}