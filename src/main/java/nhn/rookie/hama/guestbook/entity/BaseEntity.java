package nhn.rookie.hama.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class }) // JPA 내부에서 엔티티 객체가 생성/변경되는 것을 감지하는 역할(AuditingEntityListener)
@Getter
abstract class BaseEntity {

    @CreatedDate // 엔티티 생성 시간 처리
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate // 엔티티 최종 수정 시간 처리
    @Column(name = "moddate")
    private LocalDateTime modDate;

}
