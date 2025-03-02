package com.bovo.Bovo.modules.archive_detail.repository;

import com.bovo.Bovo.modules.archive_detail.domain.MyBooks;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MyBooksRepository {

    @PersistenceContext
    private EntityManager em;


    public MyBooks findOne(Integer id) {
        return em.find(MyBooks.class, id);
    }


}
