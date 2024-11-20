package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.account;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.account.AccountRepository;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

/**
 * Singleton class responsible for working with {@link com.getjavajob.training.timashovy.socialnetwork.dao.util.dbutils.TableNames#ACCOUNTS_TABLE accounts table}.
 * It provides functionality for working with data inside above-mentioned table.
 */
public class AccountDao implements AccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account save(Account account) {
        entityManager.persist(account);
        return account;
    }

    @Override
    public boolean updateById(Long id, Account account) {
        Account existingAccount = entityManager.find(Account.class, id);
        if (isNull(existingAccount)) {
            return false;
        }
        if (isFieldChanged(existingAccount.getFirstName(), account.getFirstName())) {
            existingAccount.setFirstName(account.getFirstName());
        }
        if (isFieldChanged(existingAccount.getLastName(), account.getLastName())) {
            existingAccount.setLastName(account.getLastName());
        }
        if (isFieldChanged(existingAccount.getMiddleName(), account.getMiddleName())) {
            existingAccount.setMiddleName(account.getMiddleName());
        }
        if (isFieldChanged(existingAccount.getBirthDate(), account.getBirthDate())) {
            existingAccount.setBirthDate(account.getBirthDate());
        }
        if (isFieldChanged(existingAccount.getPersonalAddress(), account.getPersonalAddress())) {
            existingAccount.setPersonalAddress(account.getPersonalAddress());
        }
        if (isFieldChanged(existingAccount.getWorkAddress(), account.getWorkAddress())) {
            existingAccount.setWorkAddress(account.getWorkAddress());
        }
        if (isFieldChanged(existingAccount.getEmail(), account.getEmail())) {
            existingAccount.setEmail(account.getEmail());
        }
        if (isFieldChanged(existingAccount.getIcq(), account.getIcq())) {
            existingAccount.setIcq(account.getIcq());
        }
        if (isFieldChanged(existingAccount.getSkype(), account.getSkype())) {
            existingAccount.setSkype(account.getSkype());
        }
        if (isFieldChanged(existingAccount.getAdditionalInfo(), account.getAdditionalInfo())) {
            existingAccount.setAdditionalInfo(account.getAdditionalInfo());
        }
        if (isFieldChanged(existingAccount.getRole(), account.getRole())) {
            existingAccount.setRole(account.getRole());
        }
        if (isFieldChanged(existingAccount.getAvatar(), account.getAvatar())) {
            existingAccount.setAvatar(account.getAvatar());
        }
        if (isFieldChanged(existingAccount.getPhones(), account.getPhones())) {
            existingAccount.setPhones(account.getPhones());
        }
        return true;
    }

    private <T> boolean isFieldChanged(T oldValue, T newValue) {
        return !Objects.equals(oldValue, newValue);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Account existingAccount = entityManager.find(Account.class, id);
        if (!isNull(existingAccount)) {
            entityManager.remove(existingAccount);
        }
    }

    @Override
    public Optional<Account> getById(Long id) {
        try {
            Account existingAccount = entityManager.find(Account.class, id);
            return Optional.ofNullable(existingAccount);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Account> getAll() {
        try {
            return entityManager.createQuery("select a from Account a", Account.class).getResultList();
        } catch (PersistenceException e) {
            return emptyList();
        }
    }

}
