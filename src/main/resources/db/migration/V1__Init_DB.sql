ALTER TABLE IF EXISTS value
  DROP CONSTRAINT IF EXISTS valueToPoint;
ALTER TABLE IF EXISTS value
  DROP CONSTRAINT IF EXISTS valueToAttribute;

DROP TABLE IF EXISTS point CASCADE;
DROP TABLE IF EXISTS attribute CASCADE;
DROP TABLE IF EXISTS value CASCADE;
DROP SEQUENCE IF EXISTS hibernate_sequence;
-- DROP SEQUENCE IF EXISTS point_id_sequence;
-- DROP SEQUENCE IF EXISTS attribute_id_sequence;
-- DROP SEQUENCE IF EXISTS value_id_sequence;

CREATE SEQUENCE hibernate_sequence
  AS BIGINT
  START 1
  INCREMENT 1;
-- CREATE SEQUENCE point_id_sequence
--   AS BIGINT
--   START 1
--   INCREMENT 1;
-- CREATE SEQUENCE attribute_id_sequence
--   AS BIGINT
--   START 1
--   INCREMENT 1;
-- CREATE SEQUENCE value_id_sequence
--   AS BIGINT
--   START 1
--   INCREMENT 1;

CREATE TABLE point
(
  id         BIGSERIAL,
  name       VARCHAR(255),
  longtitude NUMERIC(6, 3),
  latitude   NUMERIC(6, 3),
  PRIMARY KEY (id)
);
CREATE TABLE attribute
(
  id   BIGSERIAL,
  name VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE TABLE value
(
  id           BIGSERIAL,
  point_id     BIGINT NOT NULL,
  attribute_id BIGINT NOT NULL,
  value        VARCHAR(255),
  PRIMARY KEY (point_id, attribute_id)
);

-- ALTER SEQUENCE point_id_sequence
-- OWNED BY point.id;
-- ALTER SEQUENCE attribute_id_sequence
-- OWNED BY attribute.id;
-- ALTER SEQUENCE value_id_sequence
-- OWNED BY value.id;
ALTER TABLE IF EXISTS value
  ADD CONSTRAINT valueToPoint FOREIGN KEY (point_id) REFERENCES point (id);
ALTER TABLE IF EXISTS value
  ADD CONSTRAINT valueToAttribute FOREIGN KEY (attribute_id) REFERENCES attribute (id);
ALTER TABLE IF EXISTS value
  ADD CONSTRAINT pointAndAttributeUniqueConstraint UNIQUE (point_id, attribute_id);
