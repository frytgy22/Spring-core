package org.lebedeva.repository;

import lombok.extern.slf4j.Slf4j;
import org.lebedeva.model.Group;
import org.lebedeva.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class StudentRepository implements org.lebedeva.repository.Repository<Student, Integer> {

    public static final String SELECT_ALL = "SELECT students.id, first_name, last_name, age, `groups`.id, name " +
            "FROM students JOIN `groups` ON `groups`.id = students.group_id";

    public static final String SELECT_BY_ID = SELECT_ALL + " WHERE students.id = ?";

    public static final String DELETE_BY_ID = "DELETE FROM students WHERE id = ? ";

    public static final String INSERT = "INSERT INTO students(first_name, last_name, age, group_id) " +
            "VALUES(?, ?, ?, ?)";

    public static final String UPDATE = "UPDATE students SET first_name = ?, last_name = ?, age = ?, group_id = ? " +
            "WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Integer save(Student data) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, data.getFirstName());
            ps.setString(2, data.getLastName());
            ps.setInt(3, data.getAge());
            ps.setInt(4, data.getGroup().getId());
            return ps;
        }, holder);

        log.info("IN StudentRepository save {}", data);
        return Objects.requireNonNull(holder.getKey()).intValue();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void update(Student data) {
        log.info("IN StudentRepository update {}", data);

        jdbcTemplate.update(UPDATE,
                data.getFirstName(),
                data.getLastName(),
                data.getAge(),
                data.getGroup().getId(),
                data.getId());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean delete(Integer id) {
        log.info("IN StudentRepository delete {}", id);

        return jdbcTemplate.update(DELETE_BY_ID, id) != 0;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Student> findAll() {
        log.info("IN StudentRepository findAll");

        return jdbcTemplate.query(SELECT_ALL, (rs, rowNum) ->
                getStudent(rs));
    }

    @Transactional(readOnly = true)
    @Override
    public Student findById(Integer id) {
        log.info("IN StudentRepository findById {}", id);

        return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, (rs, rowNum) ->
                getStudent(rs));
    }

    public Student getStudent(ResultSet rs) throws SQLException {
        return new Student(rs.getInt("students.id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("age"),
                new Group(rs.getInt("groups.id"), rs.getString("name")));
    }
}
