package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.exception.AttributeExistsException;
import app.exception.EmptyListException;
import app.exception.WrongIdException;
import app.service.AttributeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("attribute")
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping()
    public ResponseEntity<Object> getAllAttributes() {
        try {
            List<Attribute> attributes = attributeService.getAllAttributes();
            return new ResponseEntity<>(attributes, HttpStatus.OK);
        } catch (EmptyListException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAttributeById(@PathVariable @NotNull Long id) {
        try {
            Attribute attribute = attributeService.getAttributeById(id);
            return new ResponseEntity<>(attribute, HttpStatus.OK);
        } catch (WrongIdException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/show_points_with_attribute/{id}")
    public ResponseEntity<Object> getPointsWithAttribute(@PathVariable @NotNull Long id) {
        try {
            List<Point> points = attributeService.getPointsWithAttribute(id);
            return new ResponseEntity<>(points, HttpStatus.OK);
        } catch (WrongIdException | EmptyListException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<Object> addNewAttribute(@RequestBody @Valid Attribute attribute) {
        try {
            Attribute newAttribute = attributeService.addNewAttribute(attribute);
            return new ResponseEntity<>(newAttribute, HttpStatus.CREATED);
        } catch (AttributeExistsException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAttributeById(@PathVariable @NotNull Long id) {
        try {
            attributeService.deleteAttributeById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (WrongIdException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

