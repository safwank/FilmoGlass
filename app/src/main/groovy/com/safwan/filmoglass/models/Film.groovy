package com.safwan.filmoglass.models

import groovy.transform.CompileStatic

@CompileStatic
class Film {

  String title
  int year
  float rating
  String poster

  boolean isEmpty() {
    title == null && year == 0 && rating == 0 && poster == null
  }

}