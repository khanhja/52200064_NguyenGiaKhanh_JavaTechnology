package lab08.ex01.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    @ResponseBody
    public String about() {
        return "This website was created by Gia-Khanh Nguyen, for the Java Technology course.<br>This also is a SpringBoot technology experiment.";
    }
}
