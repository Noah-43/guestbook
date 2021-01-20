package nhn.rookie.hama.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import nhn.rookie.hama.guestbook.entity.Guestbook;
import nhn.rookie.hama.guestbook.entity.QGuestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1,300).forEach(i-> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {

        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if(result.isPresent()) {

            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }

    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        // 동적으로 처리하기 위해 Q도메인 클래스를 얻어옴.(엔티티 클래스에 선언된 title, content 같은 필드들을 변수로 활용 가능)
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        // BooleanBuilder는 where문에 들어가는 조건들을 넣어주는 컨테이너라고 간주하면 됨.
        BooleanBuilder builder = new BooleanBuilder();

        // 원하는 조건을 필드 값과 결합해서 생성.
        BooleanExpression expression = qGuestbook.title.contains(keyword);

        // 만들어진 조건은 where문에 and나 or같은 키워드와 결합
        builder.and(expression);

        // BooleanBuilder는 GuestbookRepository에 추가된 QuerydslPredicateExcutor 인터페이스의 findAll() 사용 가
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword); // 조건 A

        BooleanExpression exContent = qGuestbook.content.contains(keyword); // 조건 B

        // BooleanExpression을 or()로 결합
        BooleanExpression exAll = exTitle.or(exContent);

        // 결합한 조건을 BooleanBuilder에 추가
        builder.and(exAll);

        // gt: greater than. 'gno가 0보다 크다'라는 조건을 추가.
        builder.and(qGuestbook.gno.gt(0L)); // 조건 C

        // BooleanExpression 컬럼별 조건처리
        // BooleanBuilder 조건들을 엮어주는 역할
        // ....정도로 이해해두자!

        // (A or B) and C 로 where 검색한 결과를 페이징 해서 얻어 옴.
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); // BooleanBuilder 뿐만 아니라 BooleanExpression도 파라미터로 사용 가능

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }



}
