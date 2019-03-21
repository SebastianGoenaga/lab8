package edu.eci.services;

import edu.eci.models.Car;

import edu.eci.persistences.repositories.ICarRepository;

import edu.eci.services.contracts.ICarServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CarServices implements ICarServices {

	@Autowired
	@Qualifier("CarMemoryRepository")
	private ICarRepository carRepository;

	@Override
	public List<Car> list() {
		// TODO Auto-generated method stub
		return carRepository.findAll();
	}

	@Override
	public Car create(Car car) {
		// TODO Auto-generated method stub
		if (null == car.getId())
			throw new RuntimeException("Id invalid");
		else if (carRepository.find(car.getId()) != null)
			throw new RuntimeException("The user exists");
		else
			carRepository.save(car);
		return car;
	}

	@Override
	public Car get(UUID id) {
		// TODO Auto-generated method stub
		return carRepository.find(id);
	}

	@Override
	public Car get(String name) {
		// TODO Auto-generated method stub
		return carRepository.getCarByLicence(name);
	}

	@Override
	public void update(Car car) {
		carRepository.update(car);
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Car car) {
		carRepository.delete(car);
		// TODO Auto-generated method stub

	}

}
