package com.safwan.filmoglass.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown=true)
class Film {

  String title
  int year
  String rating
  String poster

  boolean isEmpty() {
    title == null && year == 0 && rating == null && poster == null
  }

}