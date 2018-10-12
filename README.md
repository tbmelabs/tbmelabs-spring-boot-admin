# TBME Labs Configuration Server

Configuration server which allows to manage global configuration.

[![Build Status](https://travis-ci.org/tbmelabs/tbmelabs-configuration-server.svg?branch=master)](https://travis-ci.org/tbmelabs/tbmelabs-configuration-server)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=configuration-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=configuration-server)
[![Docker Pulls](https://img.shields.io/docker/pulls/tbmelabs/tbmelabs-configuration-server.svg)](https://hub.docker.com/r/tbmelabs/tbmelabs-configuration-server)

### Prerequisites

Make sure to install the following software before starting:

* [Java Development Kit 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html)
* [Maven](https://maven.apache.org/download.cgi)

### Installing

This project does not need any special installation steps.

## Running the tests

Execute unit-tests by running `mvn test`. They are managed by the [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/).
Integration-tests start with `mvn verify` using the [maven-failsafe-plugin](https://maven.apache.org/surefire/maven-failsafe-plugin/).

## Deployment

This project is deployed using maven. Run `mvn clean install` to install the artifact into your local repository.

## Built With

* [Maven](https://maven.apache.org/) - A software project management and comprehension tool.
* [Spring](https://spring.io/) - The source for modern java.

## Contributing

Please read [CONTRIBUTING.md](https://github.com/tbmelabs/tbme-tv/blob/master/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/tbmelabs/tbme-tv/tags). 

## Authors

* **Timon Borter** - *Initial work* - [bbortt](https://github.com/bbortt)

See also the list of [contributors](https://github.com/tbmelabs/tbme-tv/contributors) who participated in this project.

## License

This project is published under the terms of MIT License. For more information see the [license file](https://github.com/tbmelabs/tbmelabs-configuration-server/blob/development/LICENSE).
