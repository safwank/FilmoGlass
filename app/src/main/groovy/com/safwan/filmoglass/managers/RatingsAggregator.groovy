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
    def omdbMatch = omdbProvider.getRating(criteria)
    def flixsterMatch = flixsterProvider.getRating(criteria)
    omdbMatch.zipWith(flixsterMatch,
      { Film omdbFilm, Film flixsterFilm ->
        if (omdbFilm.isEmpty()) return flixsterFilm
        if (omdbFilm.title != flixsterFilm.title || omdbFilm.year != flixsterFilm.year) return omdbFilm

        new Film(
          title: omdbFilm.title?:flixsterFilm.title,
          year: omdbFilm.year?:flixsterFilm.year,
          rating: [omdbFilm.rating, flixsterFilm.rating]
            .findAll { it != 0 }
            .inject { acc, val -> acc == 0 ? val : (acc + val) / 2 } as float,
          poster: omdbFilm.poster?:flixsterFilm.poster
        )
      }).firstOrDefault(new Film())
  }

}