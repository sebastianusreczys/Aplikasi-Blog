package co.id.sebastianus.controller.user;

import co.id.sebastianus.dao.AuthorDao;
import co.id.sebastianus.dao.CategoryDao;
import co.id.sebastianus.dao.PostDao;
import co.id.sebastianus.dao.UserDao;
import co.id.sebastianus.entity.Author;
import co.id.sebastianus.entity.Category;
import co.id.sebastianus.entity.Post;
import co.id.sebastianus.entity.User;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

@Controller
@Slf4j
public class PostUserController {

    @Autowired
    private PostDao postDao;

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

    @GetMapping("/blogs")
    public String home(ModelMap modelMap, Pageable pageable, Authentication authentication) {

        pageable = PageRequest.of(pageable.getPageNumber(), 10);
        Page<Post> postsPage = postDao.findAll(pageable);

        getFullnameAuthor(modelMap, authentication);
        modelMap.addAttribute("posts", postsPage);

        return "home";
    }

    @GetMapping("/blogs/{slug}")
    public String viewSinglePost(ModelMap modelMap, @PathVariable("slug") String slug, Authentication authentication){

        getFullnameAuthor(modelMap, authentication);


        Optional<Post> post = this.postDao.findBySlug(slug);
        if (post != null && post.get().getBody() != null) {
            // Extract the first letter of the first word in the body
            String body = post.get().getBody().trim();
            if (!body.isEmpty()) {
                String[] words = body.split("\\s+");
                if (words.length > 0) {
                    String firstWord = words[0];
                    char firstLetter = firstWord.charAt(0);
                    log.info(" " + firstLetter);
                    modelMap.addAttribute("firstLetter", firstLetter);
                    modelMap.addAttribute("post", post.get());
                    return "/post/single-post";
                }
            }
        }
        return "post-not-found";
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
