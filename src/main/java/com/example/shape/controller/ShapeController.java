package com.example.shape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shape.model.Shape;
import com.example.shape.service.ShapeService;

@RestController
@RequestMapping("/shape")
public class ShapeController {

    ShapeService shapeService = new ShapeService();

    @GetMapping("/{type}")
    public String getShapeDetails(@PathVariable("type") String type) {
        Shape shape = shapeService.getShape(type);
        if (shape == null) {
            return "Shape not found";
        }
        return "Area: " + shape.area()
             + ", Perimeter: " + shape.perimeter();
    }
}
