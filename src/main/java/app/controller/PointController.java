package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.exception.AttributePresentException;
import app.service.AttributeService;
import app.service.PointService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/point")
public class PointController {

    private final PointService pointService;
    private final AttributeService attributeService;

    @GetMapping("/show_points")
    public ResponseEntity<List<Point>> getAllPoints() {
        List<Point> points = pointService.getAllPoints();
        if (Objects.isNull(points) || points.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @GetMapping("/show_point/{id}")
    public ResponseEntity<Point> getPointByCoordinates(@PathVariable @NotNull Long id) {
        Point point = pointService.getPointById(id);
        if (Objects.isNull(point)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(point, HttpStatus.OK);
    }


    @GetMapping("/show_point_attributes/{id}")
    public ResponseEntity<List<Attribute>> getPointAttributes(@PathVariable @NotNull Long id) {
        Point point = pointService.getPointById(id);
        List<Attribute> attributes = pointService.getPointAttributes(id);
        if (Objects.isNull(point) || attributes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }

    @PostMapping(value = "/add_point")
    public ResponseEntity<HttpStatus> addNewPoint(@RequestBody @Valid Point point) {
        pointService.addNewPoint(point);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/add_attribute_to_point")
    public ResponseEntity<String> addAttributeToPoint(@RequestParam @NotNull Long attributeId,
                                                      @RequestParam @NotNull Long pointId,
                                                      @RequestParam @NotBlank String value) {
        Attribute attribute = attributeService.getAttributeById(attributeId);
        Point point = pointService.getPointById(pointId);
        if (Objects.isNull(attribute) || Objects.isNull(point)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            pointService.addAttributeToPoint(attributeId, pointId, value);
        } catch (AttributePresentException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updatePoint(@RequestBody @Valid Point point) {
        pointService.updatePoint(point); //updating existing entities, how to save connected fields?
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete_point/{id}")
    public ResponseEntity<HttpStatus> deletePointById(@PathVariable @NotNull Long id) {
        Point point = pointService.getPointById(id);
        if (Objects.isNull(point)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pointService.deletePointById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
