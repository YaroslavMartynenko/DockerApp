package app.service;

import app.domain.Attribute;

import java.util.List;

public interface AttributeService {
    Attribute getAttributeById(Long id);

    Attribute getAttributeByName(String name);

    List<Attribute> getAllAttributes();

    void addNewAttribute(Attribute attribute);

    void updateAttribute(Attribute attribute);

    void deleteAttributeById(Long id);

    void deleteAttributeByName(String name);

}
