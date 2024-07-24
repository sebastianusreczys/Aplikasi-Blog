package co.id.sebastianus.controller.user;

import co.id.sebastianus.dao.AuthorDao;
import co.id.sebastianus.dao.CategoryDao;
import co.id.sebastianus.dao.UserDao;
import co.id.sebastianus.entity.Author;
import co.id.sebastianus.entity.Category;
import co.id.sebastianus.entity.Post;
import co.id.sebastianus.entity.User;
import co.id.sebastianus.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class CategoryUserController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorDao authorDao;

    @ModelAttribute("categories")
    List<Category> categories(){
        return categoryDao.findAll();
    }

    @GetMapping("/category/{slug}")
    public String show(ModelMap modelMap, @PathVariable("slug") String slug, Pageable pageable, Authentication authentication){


        getFullnameAuthor(modelMap, authentication);

        Category category = this.categoryDao.findBySlug(slug);
        if (category == null) {
            return "redirect:/error";
        }

        pageable = PageRequest.of(pageable.getPageNumber(), 6);
        Page<Post> posts = postService.postsByCategory(category, pageable);
        Map<String, Long> counts = postService.countPostsByCategory();
        modelMap.addAttribute("size", counts);
        modelMap.addAttribute("posts", posts);
        modelMap.addAttribute("category", category);
        postService.postsByCategory(category, pageable);
        return "/category/list";
    }

    private void getFullnameAuthor(ModelMap modelMap, Authentication authentication) {
        String username = null;
        Author author = null;

        if (authentication != null && authentication.isAuthenticated()) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            User user = userDao.findByUsername(username);
            author = authorDao.findByUser(user);
        }

        modelMap.addAttribute("username", username);
        modelMap.addAttribute("author", author);
    }
}
