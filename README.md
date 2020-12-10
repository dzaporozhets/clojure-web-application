# Sample web application built with Clojure

[![pipeline status](https://gitlab.com/dzaporozhets/clojure-web-application/badges/master/pipeline.svg)](https://gitlab.com/dzaporozhets/clojure-web-application/commits/master)

The goal of this project is to make a blank web application with authentication and tests.
It can be used as an template for starting a new project on Clojure or for learning Clojure.

Merge requests to this project are welcome!

## Index

* [Prerequisites](#prerequisites)
* [Features](#features)
* [Running](#running)
* [Database](#database)
* [Tests](#tests)

## Screenshot

Just in case you are curious how it looks when started

![Screen_Shot_2017-08-06_at_6.20.18_PM](https://gitlab.com/dzaporozhets/clojure-web-application/uploads/6d8ba305b6b5cd7c046ffda55c4ebe16/Screen_Shot_2017-08-06_at_6.20.18_PM.png)

## Prerequisites

1. [Leiningen][https://github.com/technomancy/leiningen] 2.0.0 or above installed. 
2. PostgreSQL

## Features

* Registration
* Login
* Profile page with ability to update the password and remove the account
* PostgreSQL database with migrations
* Layout with Bootstrap CSS and jQuery

### TODO

* add remember me checkbox for authentication
* add forget password feature
* add email confirmation for signup

## Running

Create the database:

    createdb sample

To start a web server for the application, run:

    lein ring server

Now visit http://localhost:3000/ to see the app running.

### Run as container

The repository contains the `Dockerfile`. You can build and run the app with Docker.
App will be running on port `5000` by default. Database is not included. 
So make sure to pass a `DATABASE_URL` to the container.

## Database

Application reads the `DATABASE_URL` environment variable for the database connection. 
If none provided, it will use the default value from `src/sample/db.clj`. 

### Migrations

Migrations are run automatically when you start the app or tests. 
If you need to manually run migrations, you can use next command:

    lein migratus migrate

## Tests

To run tests you need to setup a test database

    # create a separate database for tests
    createdb sample-test

    # run tests
    DATABASE_URL=postgresql://localhost:5432/sample-test lein test

