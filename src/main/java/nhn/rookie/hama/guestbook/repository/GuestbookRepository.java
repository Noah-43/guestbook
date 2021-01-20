package nhn.rookie.hama.guestbook.repository;

import nhn.rookie.hama.guestbook.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
}
