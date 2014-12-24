package com.safwan.filmoglass.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown=true)
class FlixsterFilmWrapper {

  @JsonProperty('total')
  int total

  @JsonProperty('movies')
  List<FlixsterFilm> films

}