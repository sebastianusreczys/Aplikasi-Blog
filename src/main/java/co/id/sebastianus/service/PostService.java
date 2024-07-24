package co.id.sebastianus.service;

import co.id.sebastianus.dao.CategoryDao;
import co.id.sebastianus.dao.PostDao;
import co.id.sebastianus.entity.Category;
import co.id.sebastianus.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private CategoryDao categoryDao;

    public Page<Post> postsByCategory(Category category, Pageable pageable){

        return postDao.findByCategory(category, pageable);
    }

    public void deletePostsById(String id) {
        postDao.deleteById(id);
    }

    public Map<String, Long> countPostsByCategory() {
        List<Object[]> results = postDao.countPostsByCategory();
        Map<String, Long> categoryPostCounts = new HashMap<>();
        for (Object[] result : results) {
            String categoryName = (String) result[0];
            Long postCount = (Long) result[1];
            categoryPostCounts.put(categoryName, postCount);
        }
        return categoryPostCounts;
    }
    public long getCountOfPostsByAuthor(String authorId) {
        return postDao.countPostsByAuthorId(authorId);
    }

}