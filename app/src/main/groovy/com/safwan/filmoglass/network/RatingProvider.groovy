package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film

interface RatingProvider {
  Film getRating(Criteria criteria)
  List<Film> getRatings(Criteria criteria)
}