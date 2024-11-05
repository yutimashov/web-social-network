package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.GroupMembershipDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.GroupMember;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

public class GroupMembershipDaoImpl implements GroupMembershipDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void sendRequest(Group group, Account account) {
        entityManager.persist(new GroupMember(account, group));
    }

    @Override
    public void makeAdmin(Long groupId, Long accountId) {
        GroupMember groupMember = entityManager.createQuery("select gm from GroupMember gm "
                        + "where gm.group.id = :groupId "
                        + "and gm.account.id = :accountId", GroupMember.class)
                .setParameter("groupId", groupId)
                .setParameter("accountId", accountId)
                .getSingleResult();
        groupMember.setAdmin(true);
    }

    @Override
    public void makeMember(Long groupId, Long accountId) {
        GroupMember groupMember = entityManager.createQuery("select gm from GroupMember gm "
                        + "where gm.group.id = :groupId "
                        + "and gm.account.id = :accountId", GroupMember.class)
                .setParameter("groupId", groupId)
                .setParameter("accountId", accountId)
                .getSingleResult();
        groupMember.setMember(true);
    }

    @Override
    public void deleteMember(Long groupId, Long accountId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            try {
                GroupMember groupMember = entityManager.createQuery("select gm from GroupMember gm "
                                + "where gm.group.id = :groupId "
                                + "and gm.account.id = :accountId", GroupMember.class)
                        .setParameter("groupId", groupId)
                        .setParameter("accountId", accountId)
                        .getSingleResult();
                entityManager.remove(groupMember);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
            }
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public boolean isAdmin(Long groupId, Long accountId) {
        try {
            return entityManager.createQuery(
                            "select 1 from GroupMember gm where gm.group.id = :groupId "
                                    + "and gm.account.id = :accountId and gm.admin = true", Integer.class)
                    .setParameter("groupId", groupId)
                    .setParameter("accountId", accountId)
                    .getSingleResult() != 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean isSubscriber(Long groupId, Long accountId) {
        try {
            return entityManager.createQuery(
                            "select 1 from GroupMember gm where gm.group.id = :groupId "
                                    + "and gm.account.id = :accountId", Integer.class)
                    .setParameter("groupId", groupId)
                    .setParameter("accountId", accountId)
                    .getSingleResult() != 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean isMember(Long groupId, Long accountId) {
        try {
            return entityManager.createQuery(
                            "select 1 from GroupMember gm where gm.group.id = :groupId "
                                    + "and gm.account.id = :accountId and gm.member = true", Integer.class)
                    .setParameter("groupId", groupId)
                    .setParameter("accountId", accountId)
                    .getSingleResult() != 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public List<Account> getRequestAccounts(Long groupId) {
        return entityManager.createQuery("select gm.account from GroupMember gm where gm.group.id = :groupId",
                        Account.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

    @Override
    public List<Account> getRegularMembers(Long groupId) {
        return entityManager.createQuery("select gm.account from GroupMember gm where gm.group.id = :groupId "
                        + "and gm.member = true and gm.admin = false", Account.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

    @Override
    public List<Account> getAdmins(Long groupId) {
        return entityManager.createQuery("select gm.account from GroupMember gm where gm.group.id = :groupId "
                        + "and gm.member = true and gm.admin = true", Account.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

}
