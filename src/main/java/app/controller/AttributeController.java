package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.service.AttributeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("attribute")
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping("/show_attributes")
    public ResponseEntity<List<Attribute>> getAllAttributes() {
        List<Attribute> attributes = attributeService.getAllAttributes();
        if (Objects.isNull(attributes) || attributes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @GetMapping("/show_points_with_attribute/{id}")
    public ResponseEntity<List<Point>> getPointsWithAttribute(@PathVariable @NotNull Long id) {
        Attribute attribute = attributeService.getAttributeById(id);
        List<Point> points = attributeService.getPointsWithAttribute(id);
        if (Objects.isNull(attribute) || points.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @PostMapping("/add_attribute")
    public ResponseEntity<HttpStatus> addNewAttribute(@RequestBody @Valid Attribute attribute) {
        attributeService.addNewAttribute(attribute);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_attribute/{id}")
    public ResponseEntity<HttpStatus> deleteAttributeById(@PathVariable Long id) {
        Attribute attribute = attributeService.getAttributeById(id);
        if (Objects.isNull(attribute)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        attributeService.deleteAttributeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
