package com.safwan.filmoglass.managers
import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import com.safwan.filmoglass.network.OmdbProvider
import rx.Observable

class RatingsAggregator {

  OmdbProvider omdbProvider

  RatingsAggregator() {
    omdbProvider = new OmdbProvider()
  }

  Observable<Film> getAverageRating(Criteria criteria) {
    omdbProvider.getRating(criteria)
  }

}