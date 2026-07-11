# Testing Documentation

Dokumentasi ini berisi penjelasan mengenai unit testing yang dilakukan pada project Google Keep CLI.

## Overview

Pada project ini dilakukan pengujian menggunakan JUnit 5 dan Mockito.

Testing difokuskan pada bagian Service untuk memastikan fungsi utama aplikasi sudah berjalan dengan benar.

Mockito digunakan agar testing dapat dilakukan tanpa harus langsung menggunakan database SQLite.

---

## Tools yang Digunakan

- Java 21
- JUnit 5
- Mockito
- JaCoCo

---

## Penggunaan Mockito

Pada proses testing digunakan Mockito untuk membuat simulasi dari bagian Repository.

Konfigurasi yang digunakan:

- `@Mock` digunakan untuk membuat objek palsu dari:
  - `NoteRepository`

- `@InjectMocks` digunakan untuk memasukkan mock tersebut ke dalam:
  - `NoteService`

Dengan cara ini, pengujian hanya fokus pada proses yang ada di `NoteService`.

---

## File Testing

File yang dilakukan pengujian:

- `NoteServiceTest.java`

Bagian yang diuji:

- `NoteService.java`

---

## Test Case

### Pengujian Fungsi Utama

Pengujian yang dibuat:

- Menambahkan note baru
- Menampilkan semua note
- Mengambil note berdasarkan ID
- Mengubah data note
- Menghapus note
- Mencari note berdasarkan keyword
- Mencari note berdasarkan label
- Pin note
- Archive note
- Restore note
- Delete note permanen

---

### Pengujian Validasi

Selain pengujian fungsi utama, dibuat juga beberapa pengujian kondisi error:

- Title note kosong
- Data note bernilai `null`
- ID note tidak valid

---

## Cara Menjalankan Test

Untuk menjalankan unit test, gunakan perintah:

```bash
mvn clean test