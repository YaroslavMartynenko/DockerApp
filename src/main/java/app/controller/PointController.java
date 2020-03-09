package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.service.impl.PointServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("point")
public class PointController {
    private final PointServiceImpl pointService;

    @Autowired
    public PointController(PointServiceImpl pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/attributes")
    public Set<Attribute> getPointAttributes(@RequestParam String coordinates) {
        return pointService.getPointByCoordinates(coordinates).getAttributes();
    }

    @PostMapping("/add_point")
    public void addNewPoint(@RequestBody Point point) {
        pointService.addNewPoint(point);
    }

    @PostMapping("/add_attribute")
    public void addPointAttribute(@RequestParam String coordinates, @RequestBody Attribute attribute) {
        Point point = pointService.getPointByCoordinates(coordinates);
        point.getAttributes().add(attribute);
        pointService.updatePoint(point);
    }
}
