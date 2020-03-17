package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.service.AttributeService;
import app.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("point")
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

    @GetMapping("/show_point")
    public ResponseEntity<Point> getPointByCoordinates(@RequestParam @NotNull BigDecimal longtitude,
                                                       @RequestParam @NotNull BigDecimal latitude) {
        Point point = pointService.getPointByCoordinates(longtitude, latitude);
        if (Objects.isNull(point)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(point, HttpStatus.OK);
    }


    @GetMapping("/show_point_attributes/{id}")
    public ResponseEntity<List<Attribute>> getPointAttributes(@PathVariable @NotNull Long id) {
        Point point = pointService.getPointById(id);
        if (Objects.isNull(point)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pointService.getPointAttributes(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add_point")
    public ResponseEntity<Point> addNewPoint(@RequestBody @Valid Point point) {
        pointService.addNewPoint(point);
        return new ResponseEntity<>(point, HttpStatus.CREATED);
    }


    @PostMapping("/add_attribute_to_point")
    public ResponseEntity<HttpStatus> addAttributeToPoint(@RequestParam @NotNull Long attributeId,
                                                          @RequestParam @NotNull Long pointId,
                                                          @RequestParam @NotBlank String value) {
        Attribute attribute = attributeService.getAttributeById(attributeId);
        Point point = pointService.getPointById(pointId);
        if (Objects.isNull(attribute) || Objects.isNull(point)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pointService.addAttributeToPoint(attributeId, pointId, value);
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
