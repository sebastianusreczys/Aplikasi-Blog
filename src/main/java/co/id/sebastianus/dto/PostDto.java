package co.id.sebastianus.dto;

import co.id.sebastianus.entity.Category;
import lombok.Data;

@Data
public class PostDto {

    private String id;
    private String id_category;
    private String title;
    private String slug;
    private String quote;
    private String body;
    private String photo;
}
