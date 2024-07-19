package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.common.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUPS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.GroupTableFields.*;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#GROUPS_TABLE groups table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class SearchGroupDaoImpl implements SearchDao<Group> {

    private static final String FIND_GROUPS = "SELECT * FROM " + GROUPS_TABLE + " WHERE " + GROUP_NAME
            + " ILIKE ? OFFSET ? LIMIT ?;";
    private static final String FIND_GROUPS_AMOUNT = "SELECT COUNT(*) AS " + TOTAL_GROUP_AMOUNT_ALIAS + " FROM "
            + GROUPS_TABLE + " WHERE " + GROUP_NAME + " ILIKE ?;";
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Group> groupRowMapper = (rs, rowNumber) -> new Group.Builder()
            .id(rs.getLong(GROUP_ID))
            .groupName(rs.getString(GROUP_NAME))
            .description(rs.getString(GROUP_DESCRIPTION))
            .accountOwnerId(rs.getLong(GROUP_OWNER_ID))
            .avatar(rs.getBinaryStream(GROUP_AVATAR))
            .build();

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Group> searchAccounts(String searchQuery, int currentPage, int recordsPerPage) {
        return jdbcTemplate.query(FIND_GROUPS, groupRowMapper, "%" + searchQuery + "%",
                currentPage * recordsPerPage - recordsPerPage, recordsPerPage);
    }

    @Override
    public int findResultsAmount(String searchQuery) {
        Integer total = jdbcTemplate.queryForObject(FIND_GROUPS_AMOUNT, Integer.class, "%" + searchQuery + "%");
        return (total != null) ? total : -1;
    }

}
