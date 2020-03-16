package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.service.AttributeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("attribute")
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping("/show_attributes")
    public List<Attribute> getAllAttributes() {
        return attributeService.getAllAttributes();
    }

    @GetMapping("/show_points_with_attribute/{id}")
    public List<Point> getPointsWithAttribute(@PathVariable Long id) {
        return attributeService.getPointsWithAttribute(id);
    }

    @PostMapping("/add_attribute")
    public void addNewAttribute(@RequestBody Attribute attribute) {
        attributeService.addNewAttribute(attribute);
    }

    @DeleteMapping("/delete_attribute/{id}")
    public void deleteAttributeById(@PathVariable Long id) {
        attributeService.deleteAttributeById(id);
    }

}
