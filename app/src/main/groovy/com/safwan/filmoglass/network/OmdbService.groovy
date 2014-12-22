package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.Film
import groovy.transform.CompileStatic
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

@CompileStatic
interface OmdbService {

  @GET('/')
  Observable<Film> getFilm(@Query('t') String title)

}