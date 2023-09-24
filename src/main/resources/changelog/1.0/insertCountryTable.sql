--liquibase formatted sql
--changeset ART:1.0-01

INSERT INTO place_countries
(countrydescr)
VALUES
     ('Russia'),
     ('Belarus'),
     ('Poland'),
     ('Kazakhstan'),
     ('Kyrgyzstan'),
     ('Latvia'),
     ('Lithuania'),
     ('Estonia'),
     ('Finland'),
     ('Armenia');