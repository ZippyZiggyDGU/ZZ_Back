package org.example.zippyziggy.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckController {

    @GetMapping("/")
    public String home() {
        return "정상 작동";
    }

}
