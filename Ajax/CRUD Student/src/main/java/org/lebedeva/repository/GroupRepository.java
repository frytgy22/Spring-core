package org.lebedeva.repository;

import lombok.extern.slf4j.Slf4j;
import org.lebedeva.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class GroupRepository implements org.lebedeva.repository.Repository<Group, Integer> {

    public static final String SELECT_ALL = "SELECT id, name FROM `groups`";
    public static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = ?";
    public static final String DELETE_BY_ID = "DELETE FROM `groups` WHERE id = ? ";
    public static final String INSERT = "INSERT INTO `groups`(name) VALUES(?)";
    public static final String UPDATE = "UPDATE `groups` SET name = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Integer save(Group data) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, data.getName());
            return ps;
        }, holder);

        log.info("IN GroupRepository save {}", data);
        return Objects.requireNonNull(holder.getKey()).intValue();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void update(Group data) {
        log.info("IN GroupRepository update {}", data);
        jdbcTemplate.update(UPDATE, data.getName(), data.getId());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean delete(Integer id) {
        log.info("IN GroupRepository delete {}", id);
        return jdbcTemplate.update(DELETE_BY_ID, id) != 0;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Group> findAll() {
        log.info("IN GroupRepository findAll");
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Group.class));
    }

    @Transactional(readOnly = true)
    @Override
    public Group findById(Integer id) {
        log.info("IN GroupRepository findById {}", id);

        return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{id}, (rs, rowNum) ->
                new Group(rs.getInt("id"),
                        rs.getString("name")
                ));
    }
}
