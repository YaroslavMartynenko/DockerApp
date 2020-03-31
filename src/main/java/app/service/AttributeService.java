package app.service;

import app.domain.Attribute;
import app.domain.Point;

import java.util.List;

public interface AttributeService {
    Attribute getAttributeById(Long id);

    List<Attribute> getAllAttributes();

    Attribute addNewAttribute(Attribute attribute);

    void deleteAttributeById(Long id);

    List<Point> getPointsWithAttribute(Long attributeId);
}

