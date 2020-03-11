package app.service.impl;

import app.domain.Attribute;
import app.repository.AttributeRepository;
import app.service.AttributeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;

    public Attribute getAttributeById(Long id) {
        return attributeRepository.findAttributeById(id);
    }

    public Attribute getAttributeByName(String name) {
        return attributeRepository.findAttributeByName(name);
    }

    public List<Attribute> getAllAttributes() {
        return attributeRepository.findAll();
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
