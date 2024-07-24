package co.id.sebastianus.dto;

import co.id.sebastianus.entity.User;
import lombok.Data;

@Data
public class AuthorDto {

    private String fullname;
    private String email;
    private String password;
}
