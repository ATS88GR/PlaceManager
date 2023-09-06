--liquibase formatted sql
--changeset ART:0001-01

INSERT INTO place_countries
(countrydescr)
VALUES
     ('Russia'),
     ('Belarus'),
     ('Poland');