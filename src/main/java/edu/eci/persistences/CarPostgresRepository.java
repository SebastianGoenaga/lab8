package edu.eci.persistences;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.eci.models.Car;
import edu.eci.persistences.repositories.ICarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Qualifier("CarPostgresRepository")
public class CarPostgresRepository implements ICarRepository {

	private String dbUrl = System.getenv().get("JDBC_DATABASE_URL");

	@Autowired
	private DataSource carDataSource;

	@Override
	public Car getCarByLicence(String licensePlate) {
		String query = "SELECT * FORM cars WHERE licensePlate = " + licensePlate + ";";
		Car car = new Car();

		try (Connection connection = carDataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			car.setLicencePlate(rs.getString("licensePlate"));
			car.setBrand(rs.getString("brand"));
			car.setId(UUID.fromString(rs.getString("id")));
			return car;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Car> findAll() {
		String query = "SELECT * FROM cars;";
		List<Car> cars = new ArrayList<>();

		try (Connection connection = carDataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Car car = new Car();
				car.setLicencePlate(rs.getString("licensePlate"));
				car.setBrand(rs.getString("brand"));
				car.setId(UUID.fromString(rs.getString("id")));
				cars.add(car);
			}
			return cars;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public Car find(UUID id) {
		String query = "SELECT  * FROM Cars where id=" + id.toString() + ";";
		Car car = new Car();

		try (Connection connection = carDataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			car.setLicencePlate(rs.getString("licensePlate"));
			car.setBrand(rs.getString("brand"));
			car.setId(UUID.fromString(rs.getString("id")));
			return car;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public UUID save(Car entity) {
		String query = "INSERT INTO cars(id, licensePlate, brand) VALUES (" + entity.getId().toString() + ","
				+ entity.getLicencePlate() + "," + entity.getBrand() + ");";
		try (Connection connection = carDataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeQuery(query);
			return entity.getId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Car entity) {
		String query = "UPDATE cars SET licensePlate = " + entity.getLicencePlate() + " AND brand = "
				+ entity.getBrand() + " WHERE id = " + entity.getId().toString() + ";";

		try (Connection connection = carDataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeQuery(query);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delete(Car o) {
		String query = "DELETE FROM users WHERE id = " + o.getId().toString() + " AND licensePlate = "
				+ o.getLicencePlate() + " AND brand = " + o.getBrand() + ";";

		try (Connection connection = carDataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remove(Long id) {
		String query = "DELETE FROM Car WHERE id = " + id + ";";

		try (Connection connection = carDataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Bean
	public DataSource carDataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}
}
