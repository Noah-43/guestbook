package nhn.rookie.hama.guestbook.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nhn.rookie.hama.guestbook.dto.GuestbookDTO;
import nhn.rookie.hama.guestbook.dto.PageRequestDTO;
import nhn.rookie.hama.guestbook.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 Annotation
public class GuestbookController {

    private final GuestbookService service; // final로 선언

    @GetMapping("/")
    public String index() {

        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list..............." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() { // 등록 화면을 보여줌
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) { // 등록 처리 후 목록 페이지로 이동

        log.info("dto..." + dto);

        // 새로 추가된 엔티티의 번호
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    // @ModelAttribute
    // 1. 파라미터로 넘겨 준 타입의 오브젝트를 자동으로 생성
    // 2. 생성된 오브젝트에 HTTP로 넘어 온 값들을 자동으로 바인딩 (여기서는 현재 page 값)
    // 3. 어노테이션이 붙은 객체가 자동으로 Model 객체에 추가되어 전달됨
    @GetMapping("/read")
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("gno: " + gno);

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
    }

}
