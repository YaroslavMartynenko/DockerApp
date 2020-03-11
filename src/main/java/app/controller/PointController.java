package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.service.impl.AttributeServiceImpl;
import app.service.impl.PointServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("point")
public class PointController {
    private final PointServiceImpl pointService;
    private final AttributeServiceImpl attributeService;

    @Autowired
    public PointController(PointServiceImpl pointService, AttributeServiceImpl attributeService) {
        this.pointService = pointService;
        this.attributeService = attributeService;
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

    @PostMapping
    public void addAttributeToPoint(@RequestBody Attribute attribute, @RequestBody Point point) {
         pointService.addAttributeToPoint(attribute, point);
    }

    @GetMapping("/show_attributes")
    public List<Attribute> getAllAttributes() {
        return attributeService.getAllAttributes();
    }

    @PostMapping("/add_attribute")
    public void addNewAttribute(@RequestBody Attribute attribute) {
        attributeService.addNewAttribute(attribute);
    }


}
