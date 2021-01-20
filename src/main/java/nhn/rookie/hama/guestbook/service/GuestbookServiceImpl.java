package nhn.rookie.hama.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nhn.rookie.hama.guestbook.dto.GuestbookDTO;
import nhn.rookie.hama.guestbook.dto.PageRequestDTO;
import nhn.rookie.hama.guestbook.dto.PageResultDTO;
import nhn.rookie.hama.guestbook.entity.Guestbook;
import nhn.rookie.hama.guestbook.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository; // 반드시 final로 선언. @RequiredArgsConstructor를 이용해서 자동으로 주입

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO---------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);

    }
}
