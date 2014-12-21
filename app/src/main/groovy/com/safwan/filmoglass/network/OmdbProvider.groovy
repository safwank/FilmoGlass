package com.safwan.filmoglass.network
import com.safwan.filmoglass.models.Criteria
import com.safwan.filmoglass.models.Film
import retrofit.RestAdapter

class OmdbProvider implements RatingProvider {

  @Override
  Film getRating(Criteria criteria) {
    new RestAdapter.Builder()
      .setEndpoint('http://www.omdbapi.com')
      .build().create(OmdbService.class).getFilm(criteria.title)
  }

  @Override
  List<Film> getRatings(Criteria criteria) {
    [getRating(criteria)]
  }

}