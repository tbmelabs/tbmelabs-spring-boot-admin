# TBME-TV

TBME-TV is a free to use streaming application built and developed
by TBME Labs organization. We do not claim any responsibility for the
ways this application is used. Every provider is responsible for his
installation on his own.

## Getting Started

Clone the repository with in your Git Bash with `git clone https://github.com/tbmelabs/tbme-tv.git` into any location.

Create a new branch (replace [BRANCH\_NAME] with the preferred name) with `git checkout -b [BRANCH_NAME]`. You might want to state the number and name of the linked issue (if exists) in the branch name.

If you have not done yet you should follow the guided installation in installing now. Right after browse into the Maven modules and run `npm install` in any web application module. Those are currently:
 * \webapp

Your local installation is now ready for changes. Remember to often stash and commit them.

Create a [pull request](https://github.com/tbmelabs/tbme-tv/compare) if you think that your changes are ready to be merged.

For more information about the Git Flow look at versioning.

### Prerequisites

* [Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Apache Maven 3.5.0](https://maven.apache.org/download.cgi)
* [Node.Js](https://nodejs.org/en/download/)
* [MySQL](https://www.mysql.com/)
* [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi)
* Your favorite IDE (We generally use Eclipse)
* [Project Lombok](https://projectlombok.org/download)

### Installing

#### Installing Java

Double-Click the executable file to start the installation. Choose any preferred installation path. Remember to adjust the environment variable called `%JAVA_HOME%`: It should point to the root folder of the Java Development Kit.

#### Preparing Maven

Double-Click the executable file to start the installation. Choose any preferred installation path. Remember to adjust the environment variable called `%M2_HOME`: It should point to the root folder of the installation.

#### Configuring Node.js

Double-Click the executable file to start the installation. Choose any preferred installation path. Make sure you select npm (Node Package Manager) to be installed if you choose to install packages manually. Check your installation with `npm -v` in any console.

#### Setting up local database (MySQL)

Double-Click the executable file to start the installation. The installation settings do not matter as long as you adjust the settings of the application. Execute the script located at `[PROJECT_ROOT]\src\main\resources\setup\__init.sql`. It will initially create the database as well as add a default Flyway and Hibernate user.

#### Installing Apache Tomcat

Double-Click the executable file to start the installation. Choose any preferred installation path. If you are willed to develop anything to the application you might want to add Tomcat to your IDE.

[TODO] End with an example of getting some data out of the system or using it for a little demo

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

In Node.JS (means your favorite console) run `npm build` to create the final build.js.

As the build has finished use `mvn clean test package install` and copy the generated .war-file from your local Maven repository into the deployment location of Apache Tomcat.

## Built With

* [Maven](https://maven.apache.org/) -Backend Dependency Management
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
