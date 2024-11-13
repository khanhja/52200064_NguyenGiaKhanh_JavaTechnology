package lab08.ex01.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    @GetMapping("/contact")
    public String showContactForm() {
        return "contact";
    }

    @PostMapping("/contact")
    public String processContactForm(
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("fullName") String fullName,
            @RequestParam("gender") String gender,
            @RequestParam("course") String course,
            @RequestParam("studentId") String studentId,
            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("phone", phone);
        model.addAttribute("fullName", fullName);
        model.addAttribute("gender", gender);
        model.addAttribute("course", course);
        model.addAttribute("studentId", studentId);

        return "result";
    }
}
