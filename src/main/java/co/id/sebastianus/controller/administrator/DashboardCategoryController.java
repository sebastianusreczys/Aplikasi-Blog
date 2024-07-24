package co.id.sebastianus.controller.administrator;

import co.id.sebastianus.dao.AuthorDao;
import co.id.sebastianus.dao.CategoryDao;
import co.id.sebastianus.dao.UserDao;
import co.id.sebastianus.entity.Author;
import co.id.sebastianus.entity.Category;
import co.id.sebastianus.entity.User;
import co.id.sebastianus.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("dashboard/category")
public class DashboardCategoryController {

    @Value("${upload.foto}")
    private String uploadFoto;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private PostService postService;

    @GetMapping("/categories")
    public String categoryLists(ModelMap modelMap, Authentication authentication){

        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        User user = userDao.findByUsername(username);

        Author author = authorDao.findByUser(user);
        modelMap.addAttribute("username", username);
        modelMap.addAttribute("author", author);

        List<Category> categories = categoryDao.findAll();
        modelMap.addAttribute("categories", categories);


        Map<String, Long> counts = postService.countPostsByCategory();

        modelMap.addAttribute("jumlah", categories.size());
        modelMap.addAttribute("size", counts);
        return "/dashboard/category/list";
    }

    @GetMapping("/form")
    public String categoryForm(ModelMap modelMap, Authentication authentication){



        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        User user = userDao.findByUsername(username);

        Author author = authorDao.findByUser(user);
        modelMap.addAttribute("username", username);
        modelMap.addAttribute("author", author);


        modelMap.addAttribute("category", new Category());
        return "dashboard/category/form";
    }

    @PostMapping("/form")
    public String categoryCreate(ModelMap modelMap, @Valid Category category, MultipartFile file, Authentication authentication) throws IOException {

        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        User user = userDao.findByUsername(username);
        modelMap.addAttribute("username", username);


        System.out.println(file.getName());
        System.out.println(file.getContentType());
        if(file.getSize() > 0){
            String nameFile= file.getName();
            String typeFile = file.getContentType();
            String originalFile= file.getOriginalFilename();
            Long size = file.getSize();
            String extension = "";

            int i = originalFile.lastIndexOf('.');
            int p = Math.max(originalFile.lastIndexOf('/'), originalFile.lastIndexOf('\\'));

            if (i > p) {
                extension = originalFile.substring(i + 1);
            }

            String idFile = UUID.randomUUID().toString();
            String lokasiUpload = uploadFoto + File.separator + category.getName();
            new File(lokasiUpload).mkdirs();
            File tujuan = new File(lokasiUpload + File.separator + idFile + "." + extension);
            file.transferTo(tujuan);
            category.setPicture(idFile + "." + extension);

        }else{
            category.setPicture(category.getPicture());
        }
        categoryDao.save(category);
        return "redirect:/dashboard/category/categories";
    }
}
