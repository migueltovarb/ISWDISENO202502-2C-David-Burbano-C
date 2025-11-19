package com.example.carcrudapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.carcrudapi.model.Car;

public interface CarRepository extends MongoRepository<Car, String> {
}
