package com.safwan.filmoglass.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown=true)
class FlixsterFilm extends Film {

  @JsonProperty('title')
  void setTitle(String title) {
    super.title = title
  }

  @JsonProperty('year')
  void setYear(int year) {
    super.year = year
  }

  @JsonProperty('ratings')
  void setRatings(Map<String, String> ratings) {
    if (!ratings?.isEmpty()) {
      super.rating = ratings.findAll { it.key?.contains('score') && it.value?.isFloat() }
        .collect { it.value?.toFloat() / 10 }
        .inject(0) { acc, val -> acc == 0 ? val : (acc + val) / 2 } as float
    }
  }

  @JsonProperty('posters')
  void setPosters(Map<String, String> posters) {
    if (!posters?.isEmpty()) {
      def nonEmptyPosters = posters.findAll { it.value != null }.collect { it.value }
      super.poster = nonEmptyPosters.any() ? nonEmptyPosters.first() : null
    }
  }

}