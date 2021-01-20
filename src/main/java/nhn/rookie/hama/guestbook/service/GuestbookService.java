package nhn.rookie.hama.guestbook.service;

import nhn.rookie.hama.guestbook.dto.GuestbookDTO;
import nhn.rookie.hama.guestbook.dto.PageRequestDTO;
import nhn.rookie.hama.guestbook.dto.PageResultDTO;
import nhn.rookie.hama.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO); // Request를 받아서 Result를 주는 서비스(헷갈리지 말자)

    // Java 8버전부터 인터페이스의 실제 내용을 가지는 코드를 default라는 키워드로 생성할 수 있다.(추상 클래스를 생략 가능)
    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook entity) {

        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
