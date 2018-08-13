#!/bin/bash
# Deployment script for TBME-TV
# Code open source hosted on: https://github.com/tbmelabs/tbme-tv

set -ev

if [[ $TRAVIS_BRANCH == "master" && $TRAVIS_PULL_REQUEST == "false" ]]; then
  # mvn versions:set deploy -DremoveSnapshot -DskipTests=true
  echo "we'll deploy releases here!"
elif [[ $TRAVIS_PULL_REQUEST == "false" ]]; then
  # mvn deploy -DskipTests=true
  echo "we'll deploy snapshot here!"
fi

exit $?
