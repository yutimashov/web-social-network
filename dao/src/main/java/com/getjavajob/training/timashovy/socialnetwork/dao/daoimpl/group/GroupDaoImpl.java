package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.domain.Group;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.BaseDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.GROUPS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.GroupTableFields.*;

public class GroupDaoImpl implements BaseDao<Group> {

    private static final String CREATE = "INSERT INTO " + GROUPS_TABLE + " (" + GROUP_NAME + ", "
            + GROUP_DESCRIPTION + ", " + GROUP_OWNER_ID + ", " + GROUP_AVATAR + ") VALUES(?, ?, ?, ?)";
    private static final String GET_GROUP_BY_ID = "SELECT " + GROUP_ID + ", " + GROUP_NAME + ", " + GROUP_DESCRIPTION
            + ", " + GROUP_OWNER_ID + ", " + GROUP_AVATAR + " FROM " + GROUPS_TABLE + " WHERE " + GROUP_ID + " = ?";
    private static final String GET_ALL_GROUPS = "SELECT " + GROUP_ID + ", " + GROUP_NAME + ", " + GROUP_DESCRIPTION
            + ", " + GROUP_OWNER_ID + ", " + GROUP_AVATAR + " FROM " + GROUPS_TABLE + ";";
    private static final String UPDATE_GROUP_BY_ID = "UPDATE " + GROUPS_TABLE + " SET " + GROUP_NAME + " = ?, "
            + GROUP_DESCRIPTION + " = ?, " + GROUP_OWNER_ID + " = ?, " + GROUP_AVATAR + " = ? WHERE " + GROUP_ID
            + " = ?";
    private static final String DELETE_GROUP_BY_ID = "DELETE FROM " + GROUPS_TABLE + " WHERE " + GROUP_ID + " = ?";

    private final RowMapper<Group> groupRowMapper = (rs, rowNum) -> new Group.Builder()
            .id(rs.getLong(GROUP_ID))
            .groupName(rs.getString(GROUP_NAME))
            .description(rs.getString(GROUP_DESCRIPTION))
            .accountOwnerId(rs.getLong(GROUP_OWNER_ID))
            .avatar(rs.getBinaryStream(GROUP_AVATAR))
            .build();

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long create(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE, new String[] { GROUP_ID });
            setGroupData(group, ps);
            return ps;
        }, keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            Long id = generatedId.longValue();
            group.setId(id);
            return id;
        }
        return null;
    }

    private void setGroupData(Group group, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, group.getGroupName());
        preparedStatement.setString(2, group.getDescription());
        preparedStatement.setLong(3, group.getAccountOwnerId());
        preparedStatement.setBinaryStream(4, group.getAvatar());
    }

    @Override
    public Optional<Group> getById(Long id) {
        return jdbcTemplate.query(GET_GROUP_BY_ID, groupRowMapper, id).stream().findFirst();
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(GET_ALL_GROUPS, groupRowMapper);
    }

    @Override
    public boolean updateById(Long id, Group group) {
        return jdbcTemplate.update(UPDATE_GROUP_BY_ID, ps -> {
            setGroupData(group, ps);
            ps.setLong(5, id);
        }) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(DELETE_GROUP_BY_ID, id) > 0;
    }

}
