package edu.eci.persistences;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.eci.models.User;
import edu.eci.persistences.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
@Qualifier("UserPostgresRepository")
public class UserPostgresRepository implements IUserRepository {

	private String dbUrl = System.getenv().get("JDBC_DATABASE_URL");;

	@Autowired
	private DataSource userdataSource;

	@Override
	public User getUserByUserName(String userName) {
		String query = "SELECT  * FROM users where name=" + userName + ";";
		User user = new User();
		try (Connection connection = userdataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			user.setName(rs.getString("name"));
			user.setId(UUID.fromString(rs.getString("id")));
			return user;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<User> findAll() {
		String query = "SELECT  * FROM users;";
		List<User> users = new ArrayList<>();

		try (Connection connection = userdataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				User user = new User();
				user.setName(rs.getString("name"));
				user.setId(UUID.fromString(rs.getString("id")));
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public User find(UUID id) {
		String query = "SELECT  * FROM users where id=" + id.toString() + ";";
		User user = new User();
		try (Connection connection = userdataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			user.setName(rs.getString("name"));
			user.setId(UUID.fromString(rs.getString("id")));
			return user;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public UUID save(User entity) {
		String query = "Insert INTO USER(id,name)VALUES("+entity.getId().toString()+","+entity.getName()+");";
		try (Connection connection = userdataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeQuery(query);
			return entity.getId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(User entity) {
		String query = "UPDATE users SET name = " + entity.getName() + " WHERE id = " + entity.getId().toString() + ";";

		try (Connection connection = userdataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Override
	public void delete(User o) {
		String query = "DELETE FROM users WHERE id = " + o.getId().toString() + " AND name = " + o.getName() + ";";

		try (Connection connection = userdataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remove(Long id) {
		String query = "DELETE FROM users WHERE id = " + id + ";";

		try (Connection connection = userdataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Bean
	public DataSource userdataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}
}
