package co.id.sebastianus.controller.administrator;

import co.id.sebastianus.dao.AuthorDao;
import co.id.sebastianus.dao.CategoryDao;
import co.id.sebastianus.dao.UserDao;
import co.id.sebastianus.entity.Author;
import co.id.sebastianus.entity.Category;
import co.id.sebastianus.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class DashboardUserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private CategoryDao categoryDao;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryDao.findAll();
    }

    @GetMapping("/users/edit")
    public String editUser(ModelMap modelMap, Authentication authentication){
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();

        User user = userDao.findByUsername(username);

        Author author = authorDao.findByUser(user);
        modelMap.addAttribute("username", username);

        modelMap.addAttribute("user", user);
        modelMap.addAttribute("author", author);
        return "/user/edit-profile";
    }
}
