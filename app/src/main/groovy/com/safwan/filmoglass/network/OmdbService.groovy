package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.Film
import retrofit.http.GET
import retrofit.http.Query

interface OmdbService {

  @GET('/')
  Film getFilm(@Query('t') String title)

}