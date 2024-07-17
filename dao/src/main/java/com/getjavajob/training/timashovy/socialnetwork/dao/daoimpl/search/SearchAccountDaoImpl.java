package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.search;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.SearchDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames.ACCOUNTS_TABLE;
import static com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.fieldsnames.AccountTableFields.*;
import static java.util.Objects.isNull;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table}.
 * It provides safe multithreading approach for creating singleton object using synchronization mechanism.
 */
public class SearchAccountDaoImpl implements SearchDao<Account> {

    private static final String FIND_ACCOUNTS = "SELECT " + ACCOUNT_ID + ", " + ACCOUNT_FIRST_NAME + ", "
            + ACCOUNT_LAST_NAME + " FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_FIRST_NAME + " ILIKE ? OR "
            + ACCOUNT_LAST_NAME + " ILIKE ? OFFSET ? LIMIT ?;";
    private static final String FIND_ACCOUNTS_AMOUNT = "SELECT COUNT(*) AS " + TOTAL_ACCOUNTS_AMOUNT_ALIAS + " FROM "
            + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_FIRST_NAME + " ILIKE ? OR " + ACCOUNT_LAST_NAME + " ILIKE ?;";
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Account> searchAccountRowMapper = (rs, rowNumber) -> new Account.Builder()
            .id(rs.getLong(ACCOUNT_ID))
            .firstName(rs.getString(ACCOUNT_FIRST_NAME))
            .lastName(rs.getString(ACCOUNT_LAST_NAME))
            .build();

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Account> searchAccounts(String searchQuery, int currentPage, int recordsPerPage) {
        return jdbcTemplate.query(FIND_ACCOUNTS, searchAccountRowMapper, "%" + searchQuery + "%",
                "%" + searchQuery + "%", currentPage * recordsPerPage - recordsPerPage, recordsPerPage);
    }

    public int findResultsAmount(String searchQuery) {
        Integer result = jdbcTemplate.queryForObject(FIND_ACCOUNTS_AMOUNT, (rs, rowNum) ->
                rs.next() ? rs.getInt(TOTAL_ACCOUNTS_AMOUNT_ALIAS) : -1, "%" + searchQuery + "%", "%" +
                searchQuery + "%");
        return !isNull(result) ? result : -1;
    }

}
