package com.bovo.Bovo.modules.archive_detail.repository;

import com.bovo.Bovo.common.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRepository {

    @PersistenceContext
    private EntityManager em;

    public Users findOne(Integer id) {
        return em.find(Users.class, id);
    }

}
