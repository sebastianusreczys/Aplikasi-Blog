INSERT INTO s_permission (id, permission_value, permission_label) VALUES
      ('tambahpostingan', 'TAMBAH_POSTINGAN', 'Tambah Postingan'),
      ('editpostingan', 'EDIT_POSTINGAN', 'Edit Postingan'),
      ('hapuspostingan', 'HAPUS_POSTINGAN', 'Hapus Postingan'),
      ('lihatpostingan', 'LIHAT_POST', 'lihat Post'),
      ('tambahcategory', 'TAMBAH_CATEGORY', 'Tambah Category'),
      ('ubahcategory', 'UBAH_CATEGORY', 'Ubah Category'),
      ('hapuscategory', 'HAPUS_CATEGORY', 'Hapus Category');

INSERT INTO s_role (id, description, name) VALUES
       ('author', 'USER', 'User'),
       ('admin', 'SUPERUSER', 'Super User');

INSERT INTO s_role_permission (id_role, id_permission) VALUES
   ('author', 'editpostingan'),
   ('author', 'tambahpostingan'),
   ('author', 'hapuspostingan'),
   ('author', 'lihatpostingan'),
   ('admin', 'tambahcategory'),
   ('admin', 'ubahcategory'),
   ('admin', 'hapuscategory');

# INSERT INTO s_user (id, active, username, id_role) VALUES
#     ('u001', true, 'user001', 'staff');
#
# INSERT INTO s_user (id, active, username, id_role) VALUES
#     ('u002', true, 'user002', 'manager');
#
# INSERT INTO s_user (id, active, username, id_role) VALUES
#     ('u003', true, 'user003', 'superuser');
#
# INSERT INTO s_user_password (id_user, password) VALUES
#     -- password : test123
#     ('u001', '$2a$13$d2GZHGr6gedUiNk8r3Pbo.Jc8eH7oBVdTta.WGMG9g1dO9T4hlNPG');
#
# INSERT INTO s_user_password (id_user, password) VALUES
#     -- password : test123
#     ('u002', '$2a$13$d2GZHGr6gedUiNk8r3Pbo.Jc8eH7oBVdTta.WGMG9g1dO9T4hlNPG');
#
# INSERT INTO s_user_password (id_user, password) VALUES
#     -- password : test123
#     ('u003', '$2a$13$d2GZHGr6gedUiNk8r3Pbo.Jc8eH7oBVdTta.WGMG9g1dO9T4hlNPG');
#
#
# INSERT INTO s_user (id, active, username, id_role) VALUES
#     ('c001', true, 'client001', 'clientapp');
#
# INSERT INTO s_user_password (id_user, password) VALUES
#     -- password : test123
#     ('c001', '$2a$13$d2GZHGr6gedUiNk8r3Pbo.Jc8eH7oBVdTta.WGMG9g1dO9T4hlNPG');
