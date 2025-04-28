package org.example.zippyziggy.Controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.zippyziggy.Domain.Magazine;
import org.example.zippyziggy.Service.MagazineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/magazine")
@RequiredArgsConstructor
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping
    public List<Magazine> getAllMagazines() {
        return magazineService.getAllMagazines();
    }

    @PostMapping
    public String createMagazine(@RequestBody MagazineRequest request) {
        magazineService.createMagazine(request.getTitle(), request.getContent());
        return "작성 완료!";
    }

    @Data
    static class MagazineRequest {
        private String title;
        private String content;
    }
}
