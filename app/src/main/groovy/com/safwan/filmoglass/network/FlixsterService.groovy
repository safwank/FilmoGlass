package com.safwan.filmoglass.network

import com.safwan.filmoglass.models.FlixsterFilmWrapper
import groovy.transform.CompileStatic
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

@CompileStatic
interface FlixsterService {

  @GET('/movies.json')
  Observable<FlixsterFilmWrapper> getFilms(@Query('apiKey') String apiKey,
                                           @Query('page_limit') int pageLimit,
                                           @Query('q') title)

}