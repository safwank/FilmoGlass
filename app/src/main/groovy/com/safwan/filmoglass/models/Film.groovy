package com.safwan.filmoglass.models

import groovy.transform.CompileStatic

@CompileStatic
class Film {

  String title
  int year
  String rating
  String poster

  boolean isEmpty() {
    title == null && year == 0 && rating == null && poster == null
  }

}