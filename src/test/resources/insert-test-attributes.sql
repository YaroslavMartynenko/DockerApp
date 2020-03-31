DELETE
FROM attribute;

ALTER SEQUENCE hibernate_sequence
RESTART;
ALTER SEQUENCE attribute_id_seq
RESTART;

INSERT INTO attribute (id, name)
VALUES (1, 'Attribute 1'),
       (2, 'Attribute 2');