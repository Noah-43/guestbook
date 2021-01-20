package nhn.rookie.hama.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO<DTO, EN> { // 다양한 곳에서 사용할 수 있도록 제네릭 타입을 이용(DTO, Entity)

    private List<DTO> dtoList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) { // Page<Entity> 타입을 이용해서 생성. Function<EN, DTO>는 엔티티 객체들을 DTO로 변환해 주는 기능

        dtoList = result.stream().map(fn).collect(Collectors.toList());

    }
}
