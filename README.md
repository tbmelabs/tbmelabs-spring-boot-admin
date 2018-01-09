# TBME TV

A free to use streaming application.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

All tests will be executed automatically within `mvn package`. This includes Java (JUnit) and JavaScript/React (Karma) tests.

Use `mvn test` to execute the JUnit tests only. Nearly every Maven module includes some test cases.

Both the modules core-server/zuul-entry-server and core-server/authorization-server include frontend tests. Navigate to src/main/webapp and execute `npm run test` to start the Karma tests.

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

You have to install [PostgreSQL](https://www.postgresql.org/) before deploying anything. Any attempt to execute `mvn test` without the proper database setup will fail. Login with your root account after the successful installation and execute the following sql-files to create the test users and databases:

* core-server/authorization-server/src/main/resources/db/installation/development/test_database.sql

### .war-files to Tomcat

The default build generates .war-files using Maven. Open a command line and execute `mvn clean install` to generate your copy of the project.

### Docker images

You may run the full application stack on Docker. It is packaged using the [com.spotify.docker-maven-plugin](https://mvnrepository.com/artifact/io.fabric8/docker-maven-plugin). To generate all images execute `mvn clean package docker:build` in the root folder.

## Built With

* [npm](https://www.npmjs.com/) - The package manager for JavaScript and the world’s largest software registry.
* [React](https://reactjs.org/) - A JavaScript library for building user interfaces.
* [Maven](https://maven.apache.org/) - A software project management and comprehension tool.
* [Spring-Cloud](https://projects.spring.io/spring-cloud/) - Provides tools to quickly build some of the common patterns in distributed systems.

## Contributing

Please read [CONTRIBUTING.md](https://github.com/tbmelabs/tbme-tv/blob/master/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/tbmelabs/tbme-tv/tags). 

## Authors

* **Timon Borter** - *Initial work* - [bbortt](https://github.com/bbortt)

See also the list of [contributors](https://github.com/tbmelabs/tbme-tv/contributors) who participated in this project.

## License

TBME TV is a free to use streaming application.
Copyright (C) 2017 [TBME Labs](https://tbmelabs.ch)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc
