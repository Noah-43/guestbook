package nhn.rookie.hama.guestbook.repository;

import nhn.rookie.hama.guestbook.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, QuerydslPredicateExecutor<Guestbook> { // QuerydslPredicateExecutor 인터페이스 추가 상속(Querydsl)
}
