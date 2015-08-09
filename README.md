# Sample web application built with Clojure

The goal of this project is to make blank web application with authentication and tests.
It can be used as template for starting new project on Clojure or for learning Clojure.

Merge requests to this projects are welcome!

## Features

* Registration
* Login
* Profile page with ability to remove account

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


Run migrations

    lein migratus migrate


To start a web server for the application, run:

    lein ring server

## License

The MIT License (MIT)

Copyright (c) 2015 Dmitriy Zaporozhets

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
