# Sample web application built with Clojure

[![build status](https://gitlab.com/dzaporozhets/clojure-web-application/badges/master/build.svg)](https://gitlab.com/dzaporozhets/clojure-web-application/commits/master)

The goal of this project is to make blank web application with authentication and tests.
It can be used as template for starting new project on Clojure or for learning Clojure.

Merge requests to this projects are welcome!

## Features

* Registration
* Login
* Profile page with ability to remove account
* PostgreSQL database with migrations
* Layout with Bootstrap css and jQuery

## Screenshot

Just in case you are curious how it looks when started

![Screenshot_from_2015-08-09_20_15_52](https://gitlab.com/dzaporozhets/clojure-web-application/uploads/ba59aa36be4d5c5660355d7e5c93e30f/Screenshot_from_2015-08-09_20_15_52.png)

## TODO

* move details of database connection from user model to somewhere
* add Remember me checkbox for authentication
* add Forget password feature
* add Email confirmation for signup
* add ability to change name and password in profile

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

Install PostreSQL and create database. Details of database connection now 
are in `src/sample/models/user.clj`. 

    createdb sample


Run migrations:

    lein migratus migrate


To start a web server for the application, run:

    lein ring server

Now visit http://localhost:3000/ to see the app running.
