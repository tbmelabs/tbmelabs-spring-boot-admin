# TBME-TV

TBME-TV is a free to use streaming application built and developed
by TBME Labs organization. We do not claim any responsibility for the
ways this application is used. Every provider is responsible for his
installation on his own.

## Getting Started

[TODO] These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

* [Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Project Lombok](https://projectlombok.org/download)
* [Apache Maven 3.5.0](https://maven.apache.org/download.cgi)
* [Node.Js](https://nodejs.org/en/download/)
* [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi)
* Your favorite IDE (We generally use Eclipse)

### Installing

[TODO] A step by step series of examples that tell you have to get a development env running

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

### Backend

Execute all JUnit-Tests with maven:
```
mvn clean test
```

### Frontent

In your Node.Js console (or any Bash with Node.Js integration) run the following code:
```
npm test
```

## Deployment

Make sure your prepared your MySQL instance as stated in installing.

Run `mvn clean test package install` and copy the .war-file from your local Maven repository into the deployment location of Apache Tomcat.

## Built With

* [Maven](https://maven.apache.org/) -Backend Dependency Management
* [Spring](https://spring.io/) - Application Weight Management
* [npm](https://www.npmjs.com/) - Frontend Dependency Management
* [React](https://facebook.github.io/react/) - A JavaScript Library for Building User Interfaces
* [Bootstrap](http://getbootstrap.com/) - Responsive Frontent Design

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/tbmelabs/tbme-tv/tags). 

## Authors

* **Timon Borter** - *Initial Contributor & Lead Developer* - [bbortt](https://github.com/bbortt)
* **Mirio Eggmann** - *Core Contributor & Developer* - [mirioeggmann](https://github.com/mirioeggmann)

See also the list of [contributors](https://github.com/tbmelabs/tbme-tv/contributors) who participated in this project.

## License

This project is licensed under the GPL License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

[TODO]

* Hat tip to anyone who's code was used
* Inspiration
* etc
