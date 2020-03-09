package app.service.impl;

import app.domain.Attribute;
import app.repository.AttributeRepository;
import app.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;

    @Autowired
    public AttributeServiceImpl(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public Attribute getAttributeById(Long id) {
        return attributeRepository.getAttributeById(id);
    }

    public Attribute getAttributeByName(String name) {
        return attributeRepository.getAttributeByName(name);
    }

    public List<Attribute> getAllAttributes() {
        return attributeRepository.getAll();
    }

    public void addNewAttribute(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    public void updateAttribute(Attribute attribute) {
        attributeRepository.save(attribute);
    }

    public void deleteAttributeById(Long id) {
        attributeRepository.deleteAttributeById(id);
    }

    public void deleteAttributeByName(String name) {
        attributeRepository.deleteAttributeByName(name);
    }
}
