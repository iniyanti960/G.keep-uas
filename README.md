## Testing

Pada project ini dilakukan pengujian sederhana menggunakan JUnit 5 dan Mockito.

Testing dilakukan pada bagian NoteService untuk memastikan fungsi utama berjalan dengan baik.

Beberapa test case yang dibuat:

- Menambahkan note dengan data yang benar
- Menambahkan note dengan judul kosong
- Menambahkan note dengan data null
- Menampilkan semua note
- Mengambil note berdasarkan ID
- Update note
- Delete note
- Search note
- Search berdasarkan label
- Pin note
- Archive note
- Restore note
- Delete note permanen

Mockito digunakan untuk membuat simulasi dari `NoteRepository`, sehingga proses testing tidak langsung menggunakan database.

Hasil testing dapat dijalankan menggunakan perintah:

```bash
mvn clean test