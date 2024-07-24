document.addEventListener('trix-file-accept', function (e){
    e.preventDefault();
})
function generateSlug() {
    const titleInput = document.getElementById('title');
    const slugInput = document.getElementById('slug');

    // Mengambil nilai dari input title
    const title = titleInput.value.trim().toLowerCase();

    // Mengganti spasi dengan tanda dash (-) dan menghapus karakter yang tidak diinginkan
    const slug = title.replace(/\s+/g, '-')  // Ganti spasi dengan dash (-)
        .replace(/[^a-zA-Z0-9-]/g, ''); // Hanya mempertahankan karakter alphanumeric dan dash

    // Memasukkan nilai slug ke dalam input slug
    slugInput.value = slug;
}



function previewImage() {
   const imgae = document.querySelector('#image');
   const  imgPreview = document.querySelector('.img-preview');

   imgPreview.style.display = 'block';

   const oFReader = new FileReader();
   oFReader.readAsDataURL(imgae.files[0]);

   oFReader.onload = function (oFREvent){
       imgPreview.src = oFREvent.target.result;
   }
}


function deleteImage() {
    const imagePreview = document.getElementById('imagePreview');
    const imageContainer = document.getElementById('imageContainer');
    const deleteButton = document.getElementById('deleteButton');
    const fileInput = document.getElementById('file');
    const fileNameInput = document.getElementById('fileName');
    const imageDeleted = document.getElementById('imageDeleted');

    // Hapus gambar dari pratinjau
    imagePreview.src = '';
    imageContainer.classList.add('d-none'); // Sembunyikan container gambar
    fileInput.value = ''; // Kosongkan nilai input file
    fileNameInput.value = ''; // Kosongkan nilai input text

    // Sembunyikan tombol hapus
    deleteButton.style.display = 'none';

    // Setel imageDeleted ke true untuk menunjukkan gambar telah dihapus
    imageDeleted.value = 'true';
}

// Pastikan untuk menambahkan event listener pada form submit untuk menjaga status gambar
document.querySelector('form').addEventListener('submit', function (e) {
    const fileInput = document.getElementById('file');
    const imageDeleted = document.getElementById('imageDeleted');

    // Jika file input kosong dan gambar tidak dihapus, setel imageDeleted ke false
    if (!fileInput.value) {
        imageDeleted.value = 'false';
    }
});


// function deleteImage() {
//     const imagePreview = document.getElementById('imagePreview');
//     const imageContainer = document.getElementById('imageContainer');
//     const deleteButton = document.getElementById('deleteButton');
//     const fileInput = document.getElementById('file');
//
//     // Hapus gambar dari pratinjau
//     imagePreview.src = '';
//     imageContainer.classList.add('d-none'); // Sembunyikan container gambar
//     fileInput.value = ''; // Kosongkan nilai input file
//
//     // Sembunyikan tombol hapus
//     deleteButton.style.display = 'none';
// }


// function deleteImage() {
//     const imagePreview = document.getElementById('imagePreview');
//     const imageContainer = document.getElementById('imageContainer');
//     const deleteButton = document.getElementById('deleteButton');
//     const fileInput = document.getElementById('file');
//     const imageDeleted = document.getElementById('imageDeleted');
//
//     // Hapus gambar dari pratinjau
//     imagePreview.src = '';
//     imageContainer.classList.add('d-none'); // Sembunyikan container gambar
//     fileInput.value = ''; // Kosongkan nilai input file
//
//     // Sembunyikan tombol hapus
//     deleteButton.style.display = 'none';
//
//     // Setel imageDeleted ke true untuk menunjukkan gambar telah dihapus
//     imageDeleted.value = 'true';
// }

// Pastikan untuk menambahkan event listener pada form submit untuk menjaga status gambar
document.querySelector('form').addEventListener('submit', function (e) {
    const fileInput = document.getElementById('file');
    const imageDeleted = document.getElementById('imageDeleted');

    // Jika file input kosong dan gambar tidak dihapus, setel imageDeleted ke false
    if (!fileInput.value) {
        imageDeleted.value = 'false';
    }
});



function generateSlug() {
    const titleInput = document.getElementById('title');
    const slugInput = document.getElementById('slug');

    // Mengambil nilai dari input judul
    const title = titleInput.value.trim().toLowerCase();

    // Mengganti spasi dengan tanda dash (-) dan menghapus karakter yang tidak diinginkan
    const slug = title.replace(/\s+/g, '-')  // Ganti spasi dengan dash (-)
        .replace(/[^a-zA-Z0-9-]/g, ''); // Hanya mempertahankan karakter alphanumeric dan dash

    // Memasukkan nilai slug ke dalam input slug
    slugInput.value = slug;
}


