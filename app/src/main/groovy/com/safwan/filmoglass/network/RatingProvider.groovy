package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import groovy.transform.CompileStatic
import rx.Observable

@CompileStatic
interface RatingProvider {
  Observable<Film> getRating(Criteria criteria)
  Observable<List<Film>> getRatings(Criteria criteria)
}