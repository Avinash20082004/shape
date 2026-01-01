package com.example.shape.service;

import com.example.shape.model.*;

public class ShapeService {

    public Shape getShape(String type) {
        if (type.equalsIgnoreCase("circle")) {
            return new Circle(5);
        } else if (type.equalsIgnoreCase("rectangle")) {
            return new Rectangle(4, 6);
        }
        return null;
    }
}