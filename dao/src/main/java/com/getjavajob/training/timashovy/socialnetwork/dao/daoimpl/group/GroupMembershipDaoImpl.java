package com.getjavajob.training.timashovy.socialnetwork.dao.daoimpl.group;

import com.getjavajob.training.timashovy.socialnetwork.dao.interfaces.GroupMembershipDao;
import com.getjavajob.training.timashovy.socialnetwork.domain.account.Account;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.Group;
import com.getjavajob.training.timashovy.socialnetwork.domain.group.GroupMember;

import javax.persistence.*;
import java.util.List;

public class GroupMembershipDaoImpl implements GroupMembershipDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void sendRequest(Group group, Account account) {
        entityManager.persist(new GroupMember(account, group));
    }

    @Override
    public void makeAdmin(Group group, Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            try {
                GroupMember groupMember = entityManager.createQuery("select gm from GroupMember gm "
                                + "where gm.group.id = :groupId "
                                + "and gm.account.id = :accountId", GroupMember.class)
                        .setParameter("groupId", group.getId())
                        .setParameter("accountId", account.getId())
                        .getSingleResult();
                groupMember.setAdmin(true);
                transaction.commit();
            } catch (NoResultException | NonUniqueResultException e) {
                transaction.rollback();
            }
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void makeMember(Group group, Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            try {
                GroupMember groupMember = entityManager.createQuery("select gm from GroupMember gm "
                                + "where gm.group.id = :groupId "
                                + "and gm.account.id = :accountId", GroupMember.class)
                        .setParameter("groupId", group.getId())
                        .setParameter("accountId", account.getId())
                        .getSingleResult();
                groupMember.setMember(true);
                transaction.commit();
            } catch (NoResultException e) {
                transaction.rollback();
            }
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteMember(Group group, Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            try {
                GroupMember groupMember = entityManager.createQuery("select gm from GroupMember gm "
                                + "where gm.group.id = :groupId "
                                + "and gm.account.id = :accountId", GroupMember.class)
                        .setParameter("groupId", group.getId())
                        .setParameter("accountId", account.getId())
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
    public boolean isAdmin(Group group, Account account) {
        try {
            Boolean isAdmin = entityManager.createQuery(
                            "select gm.admin from GroupMember gm where gm.group.id = :groupId "
                                    + "and gm.account.id = :accountId", Boolean.class)
                    .setParameter("groupId", group.getId())
                    .setParameter("accountId", account.getId())
                    .getSingleResult();
            return Boolean.TRUE.equals(isAdmin);
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean isSubscriber(Group group, Account account) {
        try {
            return entityManager.createQuery(
                            "select 1 from GroupMember gm where gm.group.id = :groupId "
                                    + "and gm.account.id = :accountId", Boolean.class)
                    .setParameter("groupId", group.getId())
                    .setParameter("accountId", account.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean isMember(Group group, Account account) {
        try {
            return entityManager.createQuery(
                            "select 1 from GroupMember gm where gm.group.id = :groupId "
                                    + "and gm.account.id = :accountId and gm.member = true", Boolean.class)
                    .setParameter("groupId", group.getId())
                    .setParameter("accountId", account.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public List<Account> getRequestAccounts(Group group) {
        return entityManager.createQuery("select gm.account from GroupMember gm", Account.class)
                .getResultList();
    }

    @Override
    public List<Account> getRegularMembers(Group group) {
        return entityManager.createQuery("select gm.account from GroupMember gm where gm.group.id = :groupId "
                        + "and gm.member = true and gm.admin = false", Account.class)
                .getResultList();
    }

    @Override
    public List<Account> getAdmins(Group group) {
        return entityManager.createQuery("select gm.account from GroupMember gm where gm.group.id = :groupId "
                        + "and gm.member = true and gm.admin = true", Account.class)
                .getResultList();
    }

}
