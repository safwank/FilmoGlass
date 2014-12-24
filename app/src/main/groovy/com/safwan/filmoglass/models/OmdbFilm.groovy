package com.safwan.filmoglass.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown=true)
class OmdbFilm extends Film {

  @JsonProperty('Title')
  void setTitle(String title) {
    super.title = title
  }

  @JsonProperty('Year') 
  void setYear(int year) {
    super.year = year
  }

  @JsonProperty('imdbRating')
  void setRating(String rating) {
    super.rating = rating?.toFloat()
  }

  @JsonProperty('Poster')
  void setPoster(String poster) {
    super.poster = poster
  }

}