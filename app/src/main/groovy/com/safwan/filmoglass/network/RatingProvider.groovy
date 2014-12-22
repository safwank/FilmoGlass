package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import rx.Observable

interface RatingProvider {
  Observable<Film> getRating(Criteria criteria)
  Observable<List<Film>> getRatings(Criteria criteria)
}