package com.bovo.Bovo.modules.archive_detail.repository;

import com.bovo.Bovo.modules.archive_detail.domain.ReadingNotes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ReadingNotesRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(ReadingNotes readingNotes) {
        em.persist(readingNotes);
    }

    public ReadingNotes memoFindOns(Integer id) {
        return em.find(ReadingNotes.class, id);
    }


}
