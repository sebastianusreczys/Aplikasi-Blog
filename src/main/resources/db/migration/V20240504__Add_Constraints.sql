ALTER TABLE posts ADD CONSTRAINT fk_post_author
    FOREIGN KEY (id_author) REFERENCES authors(id) ON DELETE CASCADE;

# ALTER TABLE s_user_password ADD CONSTRAINT fk_user_password_user
#     FOREIGN KEY (id_user) REFERENCES s_user(id) ON DELETE CASCADE;
#
# ALTER TABLE authors ADD CONSTRAINT fk_author_user
#     FOREIGN KEY (id_user) REFERENCES s_user(id) ON DELETE CASCADE;