package com.safwan.filmoglass.managers

import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import com.safwan.filmoglass.network.FlixsterProvider
import com.safwan.filmoglass.network.OmdbProvider
import rx.Observable

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
    omdbMatches.mergeWith(flixsterMatches).groupBy { it.title  }
      .flatMap {
        it.reduce { acc, val ->
          acc.rating = (acc.rating + val.rating) / 2
          acc
        }
      }
      .firstOrDefault(new Film())
  }

}