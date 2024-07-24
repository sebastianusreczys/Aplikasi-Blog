package co.id.sebastianus.controller.user;

import co.id.sebastianus.dao.CategoryDao;
import co.id.sebastianus.dto.AuthorDto;
import co.id.sebastianus.entity.Category;
import co.id.sebastianus.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequestMapping("auth/users")
public class AuthUserController {


    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryDao categoryDao;

    @ModelAttribute("categories")
    List<Category> categories(){
        return categoryDao.findAll();
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "/auth/login";
    }


    @GetMapping("/daftar")
    public String signUp(Model m){
        m.addAttribute("authorDto", new AuthorDto());
        return "/auth/sign-up";
    }

    @PostMapping("/daftar")
    public String prosesDaftar(@Valid AuthorDto authorDto,
                               BindingResult erros,
                               SessionStatus status){
        if(erros.hasErrors()){
            return "/auth/sign-up";
        }
        authorService.craeteUser(authorDto);
        return "redirect:/login";
    }
}
