#!/bin/bash
# Deployment script for TBME-TV
# Code open source hosted on: https://github.com/tbmelabs/tbme-tv

set -ev

if [[ $TRAVIS_BRANCH == "master" && $TRAVIS_PULL_REQUEST == "false" ]]; then
  # mvn deploy
  echo "deploying master release"
else
  # mvn deploy
  echo "deploying snapshot release"
fi

exit $?
