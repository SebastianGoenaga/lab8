package edu.eci.persistences;

import edu.eci.models.Car;
import edu.eci.models.User;
import edu.eci.persistences.repositories.ICarRepository;
import edu.eci.persistences.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@Qualifier("CarMemoryRepository")
public class CarMemoryRepository implements ICarRepository {

	public static List<Car> carsContainer;

	public static List<Car> getContainer() {
		if (CarMemoryRepository.carsContainer == null)
			CarMemoryRepository.carsContainer = new ArrayList<>();
		return CarMemoryRepository.carsContainer;
	}

	@Override
	public Car getCarByLicence(String licensePlate) {
		return CarMemoryRepository.getContainer().stream().filter(u -> licensePlate.equals(u.getLicencePlate())).findFirst().get();
	}

	@Override
	public List<Car> findAll() {
		return CarMemoryRepository.getContainer();
	}

	@Override
	public Car find(UUID id) {
		Optional<Car> answer = CarMemoryRepository.getContainer().stream().filter(u -> id.equals(u.getBrand()))
				.findFirst();
		return answer.isPresent() ? answer.get() : null;
	}

	@Override
	public UUID save(Car entity) {
		CarMemoryRepository.getContainer().add(entity);
		return entity.getId();
	}

	@Override
	public void update(Car entity) {
		CarMemoryRepository.carsContainer = CarMemoryRepository.getContainer().stream()
				.map(u -> u.getBrand().equals(entity.getBrand()) ? entity : u).collect(toList());
	}

	@Override
	public void delete(Car o) {
		CarMemoryRepository.carsContainer = CarMemoryRepository.getContainer().stream()
				.filter(u -> !u.getBrand().equals(o.getBrand())).collect(toList());
	}

	@Override
	public void remove(Long id) {
		CarMemoryRepository.carsContainer = CarMemoryRepository.getContainer().stream()
				.filter(u -> !u.getBrand().equals(id)).collect(toList());
	}
}
