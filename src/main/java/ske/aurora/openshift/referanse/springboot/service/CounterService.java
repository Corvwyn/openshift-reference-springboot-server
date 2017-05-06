package ske.aurora.openshift.referanse.springboot.service;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An example service that demonstrates basic database operations.
 */
@Service
public class CounterService {

    private final JdbcTemplate jdbcTemplate;

    public CounterService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Map<String, Object> getAndIncrementCounter() {
        Map<String, Object> counter = jdbcTemplate.queryForMap("SELECT value FROM counter FOR UPDATE OF value");
        jdbcTemplate.update("UPDATE counter SET value=value+1");
        return counter;
    }

    public int getCounter() {
        Integer integer = jdbcTemplate.queryForObject("SELECT value FROM counter", Integer.class);
        if (integer == null) {
            return 0;
        }
        return integer;
    }
}
