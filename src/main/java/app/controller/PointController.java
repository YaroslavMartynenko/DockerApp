package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.domain.Value;
import app.repository.ValueRepository;
import app.service.AttributeService;
import app.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("point")
public class PointController {
    private final PointService pointService;
    private final AttributeService attributeService;
    private final ValueRepository valueRepository;

    @Autowired
    public PointController(PointService pointService, AttributeService attributeService, ValueRepository valueRepository) {
        this.pointService = pointService;
        this.attributeService = attributeService;
        this.valueRepository = valueRepository;
    }

    @GetMapping("/show_points")
    public List<Point> getAllPoints() {
        return pointService.getAllPoints();
    }

    @GetMapping("/show_point")
    public Point getPointByCoordinates(BigDecimal longtitude, BigDecimal latitude) {
        return pointService.getPointByCoordinates(longtitude, latitude);
    }

    @PostMapping("/add_point")
    public void addNewPoint(@RequestBody Point point) {
        pointService.addNewPoint(point);
    }

    @PostMapping("/add_attribute_to_point")
    public void addAttributeToPoint(@RequestBody Attribute attribute, @RequestBody Point point, @RequestParam String value) {
        pointService.addAttributeToPoint(attribute, point, value);
    }

    @GetMapping("/show_attributes")
    public List<Attribute> getAllAttributes() {
        return attributeService.getAllAttributes();
    }

    @PostMapping("/add_attribute")
    public void addNewAttribute(@RequestBody Attribute attribute) {
        attributeService.addNewAttribute(attribute);
    }

    @GetMapping("/test")
    public void test() {
        Point point1 = new Point(1L, "point1", new BigDecimal(1.35), new BigDecimal(1.35), null);
        addNewPoint(point1);
        Attribute attribute1 = new Attribute(1L, "attribute1", null);
        addNewAttribute(attribute1);

        Point pointById = pointService.getPointById(1L);
        Attribute attributeById = attributeService.getAttributeById(1L);

        Value value = new Value(null, pointById, attributeById, "value1");
        valueRepository.save(value);
    }

}
