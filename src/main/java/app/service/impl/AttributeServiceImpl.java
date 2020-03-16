package app.service.impl;

import app.domain.Attribute;
import app.domain.Point;
import app.domain.Value;
import app.repository.AttributeRepository;
import app.service.AttributeService;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;

    @Override
    public Attribute getAttributeById(Long id) {
        return attributeRepository.findAttributeById(id);
    }

    @Override
    public Attribute getAttributeByName(String name) {
        return attributeRepository.findAttributeByName(name);
    }

    @Override
    public List<Attribute> getAllAttributes() {
        return attributeRepository.findAll();
    }

    @Override
    public void addNewAttribute(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    @Override
    public void updateAttribute(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    @Override
    public void deleteAttributeById(Long id) {
        attributeRepository.deleteAttributeById(id);
    }

    @Override
    public void deleteAttributeByName(String name) {
        attributeRepository.deleteAttributeByName(name);
    }

    @Override
    public List<Point> getPointsWithAttribute(Long attributeId) {
        Attribute attribute = attributeRepository.findAttributeById(attributeId);
        Hibernate.initialize(attribute.getValues());
        List<Value> values = attribute.getValues();
        List<Point> points = new ArrayList<>();
        for (Value v : values) {
            points.add(v.getPoint());
        }
        return points;
    }
}
