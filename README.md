# Test Kompetensi Android App

Aplikasi Android sederhana yang dibangun untuk tujuan kompetensi/penilaian, menampilkan fungsionalitas dasar Android seperti input form, pengecekan palindrom, navigasi antar layar, pengambilan data dari API, dan tampilan daftar dengan RecyclerView.

## Fitur Aplikasi

* **Layar Utama (First Screen):**
    * Form input untuk nama pengguna.
    * Form input untuk teks yang akan dicek apakah palindrom atau tidak.
    * Tombol "CHECK" untuk memverifikasi palindrom (hasil ditampilkan via Toast).
    * Tombol "NEXT" untuk melanjutkan ke Second Screen, membawa nama pengguna sebagai parameter.

* **Layar Kedua (Second Screen):**
    * Menampilkan sapaan "Welcome" dan nama pengguna yang diambil dari First Screen.
    * Menampilkan label "Selected User Name" yang akan diperbarui dari Third Screen.
    * Tombol "Choose a User" yang akan menavigasi ke Third Screen.

* **Layar Ketiga (Third Screen):**
    * Menampilkan daftar pengguna yang diambil dari API `https://reqres.in`.
    * Setiap item daftar menampilkan gambar profil, nama lengkap, dan email pengguna.
    * Mendukung fitur Pull-to-Refresh untuk memuat halaman data berikutnya.
    * Menampilkan indikator loading (ProgressBar) saat data sedang diambil.
    * Menampilkan pesan "No users found" (empty state) jika tidak ada data.
    * Ketika item pengguna diklik, nama pengguna yang dipilih akan dikirim kembali ke Second Screen untuk memperbarui label "Selected User Name".

## API yang Digunakan

Data pengguna diambil dari: `https://reqres.in/api/users`

* Parameter `page` dan `per_page` digunakan untuk pagination.

## Teknologi/Libraries yang Digunakan

* **Kotlin:** Bahasa pemrograman utama.
* **Android Jetpack:**
    * `androidx.appcompat`: Kompatibilitas mundur.
    * `androidx.constraintlayout`: Untuk desain layout yang fleksibel.
    * `androidx.recyclerview`: Untuk menampilkan daftar yang efisien.
    * `androidx.swiperefreshlayout`: Untuk fungsionalitas pull-to-refresh.
    * `androidx.lifecycle`: Untuk `ViewModelScope` dan `LiveData` (jika digunakan).
    * `androidx.activity`: Untuk `registerForActivityResult`.
* **Material Components for Android:** Untuk komponen UI modern (MaterialButton, TextInputLayout, MaterialCardView, ShapeableImageView).
* **Retrofit:** HTTP client untuk mengambil data dari REST API.
* **Gson:** Library untuk konversi JSON ke objek Kotlin (digunakan dengan Retrofit).
* **OkHttp Logging Interceptor:** Untuk logging HTTP requests dan responses (berguna untuk debugging).
* **Glide:** Library untuk memuat dan menampilkan gambar dari URL.
* **Kotlin Coroutines:** Untuk manajemen operasi asinkron (seperti panggilan API).

## Cara Menjalankan Proyek

1.  **Clone Repository:**
    ```bash
    git clone https://github.com/Mittzsche/KotlinTestApp.git
    ```
2.  **Buka di Android Studio:**
    Buka folder proyek hasil kloning (`KotlinTestApp`) di Android Studio.
3.  **Sync Gradle:**
    Biarkan Android Studio melakukan sinkronisasi Gradle pertama kali untuk mengunduh semua dependensi.
4.  **Jalankan Aplikasi:**
    Pilih perangkat emulator atau perangkat fisik, lalu klik tombol 'Run' (ikon segitiga hijau) di Android Studio.

## Pengaturan Project (Gradle)

Proyek ini menggunakan Gradle Version Catalogs (`libs.versions.toml`) untuk manajemen dependensi.

## Author

Ahmad Fadhel Hafizhuddin / Mittzsche

---
