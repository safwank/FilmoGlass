package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.OmdbFilm
import groovy.transform.CompileStatic
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

@CompileStatic
interface OmdbService {

  @GET('/')
  Observable<OmdbFilm> getFilm(@Query('t') String title)

}