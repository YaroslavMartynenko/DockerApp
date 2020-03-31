package app.service.impl;

import app.domain.Attribute;
import app.domain.Point;
import app.exception.AttributeExistsException;
import app.exception.EmptyListException;
import app.exception.WrongIdException;
import app.repository.AttributeRepository;
import app.repository.PointRepository;
import app.service.AttributeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final PointRepository pointRepository;

    @Override
    public Attribute getAttributeById(Long id) {
        Attribute attribute = attributeRepository.findAttributeById(id);
        if (Objects.isNull(attribute)) {
            throw new WrongIdException();
        }
        return attribute;
    }

    @Override
    public List<Attribute> getAllAttributes() {
        List<Attribute> attributes = attributeRepository.findAll();
        if (attributes.isEmpty()) {
            throw new EmptyListException();
        }
        return attributes;
    }

    @Override
    public Attribute addNewAttribute(Attribute attribute) {
        Attribute attributeFromDb = attributeRepository.findAttributeByName(attribute.getName());
        if (Objects.nonNull(attributeFromDb)) {
            throw new AttributeExistsException();
        }
        return attributeRepository.save(attribute);
    }

    @Override
    public void deleteAttributeById(Long id) {
        Attribute attribute = attributeRepository.findAttributeById(id);
        if (Objects.isNull(attribute)) {
            throw new WrongIdException();
        }
        attributeRepository.deleteAttributeById(id);
    }

    @Override
    public List<Point> getPointsWithAttribute(Long attributeId) {
        Attribute attribute = getAttributeById(attributeId);
        List<Point> points = pointRepository.findByValues_Attribute(attribute);
        if (points.isEmpty()) {
            throw new EmptyListException();
        }
        return points;
    }
}
