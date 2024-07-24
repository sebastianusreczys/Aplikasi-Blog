package co.id.sebastianus.controller.administrator;

import co.id.sebastianus.dao.*;
import co.id.sebastianus.entity.*;
import co.id.sebastianus.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("dashboard/post")
@Transactional
@Slf4j
public class DashboardPostController {

    @Value("${upload.foto}")
    private String uploadFoto;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPasswordDao userPasswordDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(DashboardPostController.class);

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryDao.findAll();
    }

    @GetMapping("/list")
    public String allPosts(ModelMap modelMap, Pageable pageable, Authentication authentication){

        String username = authentication.getName();
        User user = userDao.findByUsername(username);
        Author author = authorDao.findByUser(user);
        modelMap.addAttribute("username", username);
        modelMap.addAttribute("author", author);

        pageable = PageRequest.of(pageable.getPageNumber(), 10);

        Page<Post> postsPage = postDao.findByAuthor(author,pageable);
        modelMap.addAttribute("posts", postsPage);
        modelMap.addAttribute("size", postsPage.getTotalElements());

        return "/dashboard/post/post-list";
    }

    @GetMapping("/{slug}")
    public String view(ModelMap modelMap, @PathVariable("slug") String slug, Authentication authentication){
        String username = authentication.getName();
        User user = userDao.findByUsername(username);
        Author author = authorDao.findByUser(user);
        modelMap.addAttribute("username", username);
        modelMap.addAttribute("author", author);
        Optional<Post> post = this.postDao.findBySlug(slug);
        modelMap.addAttribute("post", post.get());
        return "/dashboard/post/single-post";
    }

    @PostMapping("/hapus")
    public String deleted(@RequestParam(value = "id", required = false) String id){
        postService.deletePostsById(id);
        return "redirect:/dashboard/post/list";
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> findTitleContainingIgnoreCase(@RequestParam(value = "title") String title){
        List<Post> posts = postDao.findByTitleContainingIgnoreCase(title);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/form")
    public String postFrom(ModelMap modelMap,  @RequestParam( required = false, name = "id") String id, Authentication authentication){

        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        User user = userDao.findByUsername(username);

        Author author = authorDao.findByUser(user);
        modelMap.addAttribute("username", username);
        modelMap.addAttribute("author", author);

            modelMap.addAttribute("post", new Post());
            modelMap.addAttribute("categories", categoryDao.findAll());
            if (id != null && !id.isEmpty()){
            Post post= postDao.findById(id).get();
                modelMap.addAttribute("post", new Post());
                if (post != null){
                    modelMap.addAttribute("post", post);
                }
            }
        return "/dashboard/post/create-post";
    }

    @PostMapping("/form")
    public String postCreate(ModelMap modelMap, @Valid Post post, @RequestParam String id,
                             MultipartFile file, Authentication authentication
                             ) throws IOException {
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        User user = userDao.findByUsername(username);

        Author author = authorDao.findByUser(user);

        modelMap.addAttribute("username", username);
        post.setAuthor(author);
        System.out.println(file.getName());
        System.out.println(file.getContentType());
        post.setId(id);
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
            String lokasiUpload = uploadFoto + File.separator + post.getTitle();
            new File(lokasiUpload).mkdirs();
            File tujuan = new File(lokasiUpload + File.separator + idFile + "." + extension);
            file.transferTo(tujuan);
            post.setPhoto(idFile + "." + extension);
        }else{
            post.setPhoto(post.getPhoto());
        }
        postDao.save(post);
        return "redirect:/dashboard/post/list";
    }

    @GetMapping("/upload/{foto}/blog/")
    public ResponseEntity<byte[]> getPhotoByPost(@PathVariable Post foto, Model model) throws Exception {
        String lokasiFile = uploadFoto + File.separator + foto.getTitle()
                + File.separator + foto.getPhoto();
        try {
            HttpHeaders headers = new HttpHeaders();
            if (foto.getPhoto().toLowerCase().endsWith("jpeg") || foto.getPhoto().toLowerCase().endsWith("jpg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (foto.getPhoto().toLowerCase().endsWith("png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (foto.getPhoto().toLowerCase().endsWith("pdf")) {
                headers.setContentType(MediaType.APPLICATION_PDF);
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
            byte[] data = Files.readAllBytes(Paths.get(lokasiFile));
            return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/upload/{picture}/category/")
    public ResponseEntity<byte[]> getPhotoByCategory(@PathVariable Category picture, Model model) throws Exception {
        String lokasiFile = uploadFoto + File.separator + picture.getName()
                + File.separator + picture.getPicture();
        try {
            HttpHeaders headers = new HttpHeaders();
            if (picture.getPicture().toLowerCase().endsWith("jpeg") || picture.getPicture().toLowerCase().endsWith("jpg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (picture.getPicture().toLowerCase().endsWith("png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (picture.getPicture().toLowerCase().endsWith("pdf")) {
                headers.setContentType(MediaType.APPLICATION_PDF);
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
            byte[] data = Files.readAllBytes(Paths.get(lokasiFile));
            return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
