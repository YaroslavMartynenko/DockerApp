package app.controller;

import app.domain.Attribute;
import app.domain.Point;
import app.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("point")
public class PointController {

    private final PointService pointService;

    @GetMapping("/show_points")
    public List<Point> getAllPoints() {
        return pointService.getAllPoints();
    }

    @GetMapping("/show_point")
    public Point getPointByCoordinates(@RequestParam BigDecimal longtitude, @RequestParam BigDecimal latitude) {
        return pointService.getPointByCoordinates(longtitude, latitude);
    }

    @GetMapping("/show_point_attributes/{id}")
    public List<Attribute> getPointAttributes(@PathVariable Long id) {
        return pointService.getPointAttributes(id);
    }

    @PostMapping("/add_point")
    public void addNewPoint(@RequestBody Point point) {
        pointService.addNewPoint(point);
    }

    @PostMapping("/add_attribute_to_point")
    public void addAttributeToPoint(@RequestParam Long attributeId, @RequestParam Long pointId, @RequestParam String value) {
        pointService.addAttributeToPoint(attributeId, pointId, value);
    }

    @DeleteMapping("/delete_point/{id}")
    public void deletePointById(@PathVariable Long id) {
        pointService.deletePointById(id);
    }

}
