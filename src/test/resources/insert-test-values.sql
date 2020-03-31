DELETE
FROM value;

ALTER SEQUENCE hibernate_sequence
RESTART;
ALTER SEQUENCE value_id_seq
RESTART;

INSERT INTO value (id, point_id, attribute_id, value)
VALUES (1, 1, 1, 'Attribute 1 value'),
       (2, 1, 2, 'Attribute 2 value');


