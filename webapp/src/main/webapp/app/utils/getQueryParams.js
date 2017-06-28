'use strict';

export default function getQueryParams(query_search) {
  query_search = query_search.split('+').join(' ');

  var params = {},
    tokens,
    regex = /[?&]?([^=]+)=([^&]*)/g;

  while (tokens = regex.exec(query_search)) {
    params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
  }

  return params;
}