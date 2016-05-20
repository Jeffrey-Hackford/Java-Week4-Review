# _Band_Tracker_

#### _Band Tracker application, 5-18-2016_

#### By _**Jeffrey Hackford**_

## Description

_This app will allow users to input Band names and assign them to a Venue, Update the band name, and delete the band_

## Setup/Installation Requirements

* - Clone from github
* - clone database from sql file in project folder
* - run the terminal command "CREATE DATABASE band_tracker;"
* - then run the command "psql band_tracker < band_tracker.sql"
* OR manually set up the database by running the following commands:
* -- CREATE DATABASE band_tracker;
* -- \c band_tracker
* -- CREATE TABLE bands (id serial PRIMARY KEY, bandname varchar);
* -- CREATE TABLE venues (id serial PRIMARY KEY, venuename varchar);
* -- CREATE TABLE bands_venues (id serial PRIMARY KEY, band_id int, venue_id int);
* -- CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker;
* - run postgres in terminal
* - execute 'gradle run' in command line
* - in a web browser navigate to localhost:4567

## Known Bugs

_no known bugs at this time_

## Support and contact details

Jeffrey.hackford@gmail.com

## Technologies Used

_Java_
_Spark_
_Velocity_
_Gradle_
_Postgres_
_Sql2o_

### License

*Mit License*

Copyright (c) 2016 **_Jeffrey Hackford_**
