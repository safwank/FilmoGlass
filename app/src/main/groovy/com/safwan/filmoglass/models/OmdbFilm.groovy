package com.safwan.filmoglass.models

import com.fasterxml.jackson.annotation.JsonProperty

class OmdbFilm extends Film {

  @JsonProperty('Title') String title
  @JsonProperty('Year') int year
  @JsonProperty('imdbRating') String rating
  @JsonProperty('Poster') String poster

}