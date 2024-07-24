package co.id.sebastianus.controller.user;

import co.id.sebastianus.dao.AuthorDao;
import co.id.sebastianus.dao.CategoryDao;
import co.id.sebastianus.entity.Author;
import co.id.sebastianus.entity.Category;
import co.id.sebastianus.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthorUserController {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private PostService postService;

    @ModelAttribute("categories")
    List<Category> categories(){
        return categoryDao.findAll();
    }

    @GetMapping("/post/{id}/author")
    public String findPostByAuthorId(@PathVariable("id") String id, ModelMap modelMap,
                                     Authentication authentication){

        modelMap.addAttribute("count", postService.getCountOfPostsByAuthor(id));

        Optional<Author> authorOptional = authorDao.findById(id);
        modelMap.addAttribute("author", authorOptional.get());
        Author author = authorOptional.get();
        modelMap.addAttribute("posts", author.getPosts());
        return "/author/list";
    }
}
