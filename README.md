# Sample web application built with Clojure

[![pipeline status](https://gitlab.com/dzaporozhets/clojure-web-application/badges/master/pipeline.svg)](https://gitlab.com/dzaporozhets/clojure-web-application/commits/master)

The goal of this project is to make a blank web application with authentication and tests.
It can be used as an template for starting a new project on Clojure or for learning Clojure.

Merge requests to this project are welcome!

## Features

* Registration
* Login
* Profile page with ability to update the password and remove the account
* PostgreSQL database with migrations
* Layout with Bootstrap CSS and jQuery

## Screenshot

Just in case you are curious how it looks when started

![Screen_Shot_2017-08-06_at_6.20.18_PM](https://gitlab.com/dzaporozhets/clojure-web-application/uploads/6d8ba305b6b5cd7c046ffda55c4ebe16/Screen_Shot_2017-08-06_at_6.20.18_PM.png)

## TODO

* add remember me checkbox for authentication
* add forget password feature
* add email confirmation for signup
* add an ability to change name in profile

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

Install PostreSQL and create database. Details of database connection now 
are in `src/sample/db.clj`. 

    createdb sample

Run migrations:

    lein migratus migrate

To start a web server for the application, run:

    lein ring server

Now visit http://localhost:3000/ to see the app running.

## Tests

To run tests you need to setup test database

    # create separate database for tests
    createdb sample-test

    # pass database url to migration
    DATABASE_URL=postgresql://localhost:5432/sample-test lein migratus migrate

    # run tests
    DATABASE_URL=postgresql://localhost:5432/sample-test lein test
