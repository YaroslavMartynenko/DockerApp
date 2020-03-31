DELETE
FROM point;

ALTER SEQUENCE hibernate_sequence
RESTART;
ALTER SEQUENCE point_id_seq
RESTART;

INSERT INTO point (id, name, longtitude, latitude)
VALUES (1, 'Point 1', 1.0, 1.0),
       (2, 'Point 2', 2.0, 2.0);