package com.bovo.Bovo.modules.archive_detail.repository;

import com.bovo.Bovo.common.MyBooks;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MyBooksRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(MyBooks myBooks){
        em.persist(myBooks);
    }

    public void delete(MyBooks myBooks) {
        em.remove(myBooks);
    }

    public MyBooks findOne(Integer id) {
        return em.find(MyBooks.class, id);
    }


}
