//package com.bovo.Bovo.initializer;
//
//import com.bovo.Bovo.common.MyBooks;
//import com.bovo.Bovo.common.ReadingStatus;
//import com.bovo.Bovo.common.Users;
//import com.bovo.Bovo.modules.archive.repository.ArchiveRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class MyBooksDummy implements CommandLineRunner {
//
//    private final ArchiveRepository archiveRepository;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public MyBooksDummy(ArchiveRepository archiveRepository) {
//        this.archiveRepository = archiveRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 기존의 my_books 테이블 데이터를 삭제합니다.
//        archiveRepository.deleteAll();
//        System.out.println("Deleted existing MyBooks data.");
//
//        // EntityManager를 통해 Users 엔티티 데이터를 직접 조회합니다.
//        List<Users> users = entityManager.createQuery("SELECT u FROM Users u", Users.class).getResultList();
//        List<MyBooks> booksToSave = new ArrayList<>();
//
//        for (Users user : users) {
//            int uniqueValue = user.getId(); // 사용자 고유값을 활용해 데이터에 변화를 줍니다.
//
//            // 첫 번째 책 데이터 (사용자마다 고유하게 생성)
//            MyBooks book1 = MyBooks.builder()
//                    .users(user)
//                    .isbn("978316148410" + uniqueValue)
//                    .bookName("책제목" + uniqueValue)
//                    .bookAuthor("저자" + uniqueValue)
//                    .bookCover("책표지" + uniqueValue + ".jpg")
//                    .publicationDate(LocalDate.of(2020, 1, 1).plusDays(uniqueValue))
//                    .readingStartDate(LocalDate.of(2021, 1, 10).plusDays(uniqueValue))
//                    .readingEndDate(LocalDate.of(2021, 2, 10).plusDays(uniqueValue))
//                    .bookScore(BigDecimal.valueOf(4).add(BigDecimal.valueOf(0.1 * uniqueValue)))
//                    .bookTotalPages(350 + uniqueValue)
//                    .bookCurrentPages(350 + uniqueValue)
//                    .readingStatus(ReadingStatus.COMPLETED) // 실제 enum 값에 맞게 조정
//                    .recentlyCorrectionDate(LocalDate.now())
//                    .build();
//
//            // 두 번째 책 데이터 (사용자마다 고유하게 생성)
//            MyBooks book2 = MyBooks.builder()
//                    .users(user)
//                    .isbn("978123456789" + uniqueValue)
//                    .bookName("책제목" + uniqueValue)
//                    .bookAuthor("저자" + uniqueValue)
//                    .bookCover("책표지" + uniqueValue + ".jpg")
//                    .publicationDate(LocalDate.of(2019, 5, 15).plusDays(uniqueValue))
//                    .readingStartDate(LocalDate.of(2020, 6, 1).plusDays(uniqueValue))
//                    .readingEndDate(LocalDate.of(2020, 7, 1).plusDays(uniqueValue))
//                    .bookScore(BigDecimal.valueOf(3).add(BigDecimal.valueOf(0.1 * uniqueValue)))
//                    .bookTotalPages(200 + uniqueValue)
//                    .bookCurrentPages(200 + uniqueValue)
//                    .readingStatus(ReadingStatus.READING) // 실제 enum 값에 맞게 조정
//                    .recentlyCorrectionDate(LocalDate.now())
//                    .build();
//
//            booksToSave.add(book1);
//            booksToSave.add(book2);
//        }
//
//        // MyBooksRepository를 사용해 한 번에 저장
//        archiveRepository.saveAll(booksToSave);
//        System.out.println("Inserted " + booksToSave.size() + " book records.");
//    }
//}