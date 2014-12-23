package com.safwan.filmoglass.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown=true)
class Film {

  @JsonProperty('Title') String title
  @JsonProperty('Year') int year
  @JsonProperty('imdbRating') String rating
  @JsonProperty('Poster') String poster

  boolean isEmpty() {
    title == null && year == 0 && rating == null && poster == null
  }

}