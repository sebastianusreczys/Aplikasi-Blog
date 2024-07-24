package co.id.sebastianus.service;

import co.id.sebastianus.dao.AuthorDao;
import co.id.sebastianus.dao.RoleDao;
import co.id.sebastianus.dao.UserDao;
import co.id.sebastianus.dao.UserPasswordDao;
import co.id.sebastianus.dto.AuthorDto;
import co.id.sebastianus.entity.Author;
import co.id.sebastianus.entity.Role;
import co.id.sebastianus.entity.User;
import co.id.sebastianus.entity.UserPassword;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserPasswordDao userPasswordDao;

    @Autowired
    private AuthorDao authorDao;


    public void craeteUser(AuthorDto authorDto){

        Author author = new Author();
        User cekUsername = userDao.findByUsername(authorDto.getEmail());

        if(cekUsername == null){
            User user = new User();
            Role role = roleDao.findById("author").get();
            user.setRole(role);
            user.setUsername(authorDto.getEmail());
            user.setActive(true);
            userDao.save(user);

            UserPassword userPassword = new UserPassword();
            userPassword.setUser(user);
            userPassword.setPassword(encoder.encode(authorDto.getPassword()));
            userPasswordDao.save(userPassword);

            author.setFullname(authorDto.getFullname());
            BeanUtils.copyProperties(authorDto, author);
            author.setUser(user);
            authorDao.save(author);
        }

    }

}
