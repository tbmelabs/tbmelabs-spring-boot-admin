#!/bin/bash
# @author bbortt
# Prepares flat Angular-CLI folder structure for packaging via Maven

# prepare folders
mkdir ./dist/browser/public

# copy resources
{
  mv ./dist/browser/*.css ./dist/browser/public;
  mv ./dist/browser/*.js ./dist/browser/public;
} || {
  # something failed.. exit with error
  exit 1;
}

# exit with success
exit 0;
