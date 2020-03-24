package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.exception.AttributePresentsException;
import app.exception.EmptyListException;
import app.exception.PointExistsException;
import app.exception.WrongIdException;
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

@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/point")
public class PointController {

    private final PointService pointService;

    @GetMapping()
    public ResponseEntity<Object> getAllPoints() {
        try {
            List<Point> points = pointService.getAllPoints();
            return new ResponseEntity<>(points, HttpStatus.OK);
        } catch (EmptyListException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPointById(@PathVariable @NotNull Long id) {
        try {
            Point point = pointService.getPointById(id);
            return new ResponseEntity<>(point, HttpStatus.OK);
        } catch (WrongIdException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/show_point_attributes/{id}")
    public ResponseEntity<Object> getPointAttributes(@PathVariable @NotNull Long id) {
        try {
            List<Attribute> attributes = pointService.getPointAttributes(id);
            return new ResponseEntity<>(attributes, HttpStatus.OK);
        } catch (WrongIdException | EmptyListException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping()
    public ResponseEntity<Object> addNewPoint(@RequestBody @Valid Point point) {
        try {
            pointService.addNewPoint(point);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PointExistsException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/add_attribute_to_point")
    public ResponseEntity<Object> addAttributeToPoint(@RequestParam @NotNull Long attributeId,
                                                      @RequestParam @NotNull Long pointId,
                                                      @RequestParam @NotBlank String value) {
        try {
            pointService.addAttributeToPoint(attributeId, pointId, value);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (WrongIdException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AttributePresentsException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePointById(@PathVariable @NotNull Long id) {
        try {
            pointService.deletePointById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (WrongIdException e) {
            log.warn("Error while executing request", e.getCause());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
