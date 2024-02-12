# STEP BY STEP CRUD SPRING BOOT


[![N|Solid](https://cldup.com/dTxpPi9lDf.thumb.png)](https://nodesource.com/products/nsolid)

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

## Step 1
Membaut Project baru melalui https://start.spring.io/ agar di dalam project terdapat pom.xml
## 	Dependency awal pada saat membuat project 
- Lombok (ini buat mempersingkat code biar gak pake getter setter)
- Spring web
- Spring dev tools (biar auto reload)
Dependency lanjutan jika project ingin menggunkan db mysql
- Mysql drive
- Spring data JPA

## Strucktur folder yang di saran kan
-	Controllers
-	Helpers
-	Models
-- Entity
-- Repo
-	Services

>  Jika project menggunakan db maka harus membuaat CONFIGURASI untuk terhubung ke db nya
## Configurasi connection database

Configurasi di tulis di file bernama `appilaction.properties`
Berikut configursai yang di tulis :

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/demoapi
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

•	spring.datasource.url teridiri dari 
| Plugin | README |
| ------ | ------ |
|mysql|jika Anda menggunkan db mysql|
|127.0.0.1|alamat db nya bisa nya juga di tulis dengan “//localhost”|
|3306|ini adalaah port nya|
|demoapi|Ini adalah nama db yang akan di gunakaan|

> sebelum mengkoneksikan bisaa di lihat url,port dan nama db terlebih dahulu. 
> username dan password di sinih menggunakan root di karnakan memaang belum di setting user dan passwordnya ini secara defult jika menggunkan mysql.

•	spring.jpa.hibernate.ddl-auto 
| Plugin | README |
| ------ | ------ |
|Create|Hibernasi pertama-tama menghapus tabel yang ada, lalu membuat tabel baru|
|Update|Model objek yang dibuat berdasarkan pemetaan (anotasi atau XML) dibandingkan dengan skema yang ada, lalu Hibernate memperbarui skema sesuai dengan perbedaannya. Itu tidak pernah menghapus tabel atau kolom yang ada meskipun tidak diperlukan lagi oleh aplikasi|
|Creaate-drop|Mirip dengan create, dengan tambahan bahwa Hibernate akan menghapus database setelah semua operasi selesai. Biasanya digunakan untuk pengujian unit|
|Validate|Hibernate hanya memvalidasi apakah tabel dan kolom ada, jika tidak maka akan memunculkan pengecualian|
|None|Nilai ini secara efektif mematikan generasi DDL|

> Spring Boot secara internal menetapkan nilai parameter ini ke create-drop jika tidak ada manajer skema yang terdeteksi, jika tidak, tidak ada untuk semua kasus lainnya.

•	spring.jpa.show-sql
`spring.jpa.show-sql` adalah properti konfigurasi dalam aplikasi Spring Boot yang menentukan apakah Hibernate akan mencetak SQL yang dihasilkan ke konsol atau tidak. Properti ini mengontrol apakah Hibernate akan menampilkan atau menyembunyikan pernyataan SQL yang dihasilkan saat menjalankan aplikasi.
Jenis properti `spring.jpa.show-sql` adalah `boolean`, yang berarti nilainya dapat berupa `true` atau `false`. Berikut adalah penjelasan singkat tentang jenis properti ini:
| Plugin | README |
| ------ | ------ |
|True|Hibernate akan mencetak setiap pernyataan SQL yang dihasilkan oleh aplikasi ke konsol. Ini termasuk pernyataan SQL untuk pembuatan tabel, pengepakan, dan kueri yang dieksekusi.|
|false|Hibernate tidak akan mencetak pernyataan SQL ke konsol. Ini berguna untuk mematikan pencetakan SQL dalam lingkungan produksi untuk mengurangi jumlah informasi yang diungkapkan kepada pengguna akhir.|

Contoh log : 
```sh
Hibernate: insert into tbl_products (product_desription,product_name,price) values (?,?,?)
```

## Flow secara garis besarnya dalam spring boot
 `controller memanggil service ,service memanggil repository`
 
## Urutan penulisa biasanya
-	 Model
--	Membuat Entity (bisanya untuk membuat setter getter dan constractor atau structur db nya)
--	Membuaaat class repository (untuk memanipulasi data di table atau di sebut DAO)
-	Membuat service (berisi flow dan logic)
-	Membuat controller  (membuat endpoint)

## Contoh Code
### code Entity
```sh
package com.example.demoapis.models.entity;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_products") // saat di run jpa akan mengecek apakah di db ada table tbl_products jika belum dia akan membuat table, dan akan menghubungkan ke class product
public class Products  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement ID
    private Long id;

    @Column(name = "product_name", length = 100)
    private String name;

    @Column(name = "product_desription", length = 500)
    private String description;

    private String price;

    // constraction kosong
    public Products() {
    }

    // constraction
    public Products(Long id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // getter setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
```
### Code class repo
```sh
package com.example.demoapis.models.repo;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.demoapis.models.entity.Products;

public interface ProductRepo  extends CrudRepository<Products, Long>{

    //costoum function sercing by name
    List<Products> findByNameContains(String name);
    
}
```
### Code Service
```sh
package com.example.demoapis.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demoapis.models.entity.Products;
import com.example.demoapis.models.repo.ProductRepo;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    
    //inject ke repositorynya
    @Autowired
    private ProductRepo productRepo;

    //crerate
    public Products save(Products product){
        return productRepo.save(product);
    }

    // cari berdasrkan id
    public Products findOne(Long id){
        Optional<Products> products = productRepo.findById(id);
        if (!products.isPresent()) {
            return null;
        }
        return products.get();
    }

    // cari all
    public Iterable<Products> findAll(){
        return productRepo.findAll();
    }

    // delete
    public void removeOne(Long id){
         productRepo.deleteById(id);
    }

    //memanggil function dari repo
    public List<Products> findByName(String name){
        return productRepo.findByNameContains(name);
    }
}
```
### Code controller
```sh
package com.example.demoapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demoapis.models.entity.Products;
import com.example.demoapis.services.ProductService;

@RestController
@RequestMapping("api/products")
public class ProductController {

    //inject service nya
    @Autowired
    private ProductService productService;
    
    //save
    @PostMapping
    public Products create(@RequestBody Products products){
        return productService.save(products);
    }

    //get all
    @GetMapping
    public Iterable<Products> findAll(){
        return productService.findAll();
    }

    //get by id
    @GetMapping("/{id}")
    public Products findOne(@PathVariable("id") Long id){
        return productService.findOne(id);
    }

    //update
    @PutMapping
    public Products update(@RequestBody Products products){
        return productService.save(products);
    }

    //delete by id
    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable("id") Long id){
        productService.removeOne(id);
    }
}
```
## Penjelasan Anotasi
| Anotasi | README |
| ------ | ------ |
|@Entity|ditempatkan di atas definisi kelas Java untuk menandai bahwa kelas tersebut adalah entitas yang dapat disimpan dalam basis data.|
|@Table|ditempatkan di atas definisi kelas Java yang dianotasi dengan @Entity untuk menyesuaikan konfigurasi tabel.|
|@Id|Ini menginformasikan Hibernate bahwa atribut tersebut akan digunakan sebagai identifier unik untuk entitas saat disimpan dalam database.|
|@GeneratedValue(strategy = GenerationType.IDENTITY)|Bisa di sebut untuk Autoincrement atau salah satu dari beberapa jenis strategi generasi yang didukung oleh JPA. Ketika Anda menggunakan GenerationType.IDENTITY, Hibernate akan menggunakan kolom identitas dalam database untuk menghasilkan nilai primary key. Ini cocok untuk basis data yang mendukung kolom identitas (seperti MySQL).|
|@Column|Dengan menggunakan anotasi @Column, Anda dapat menyesuaikan nama kolom, tipe data, constraint, dan parameter lainnya.|
|@Service|untuk menandai sebuah kelas sebagai bagian dari layer layanan (service layer) dalam aplikasi. Layer layanan bertanggung jawab untuk logika bisnis aplikasi, pemrosesan data, dan koordinasi antar komponen.|
|@Transactional|dapat diterapkan pada metode atau kelas. Ketika diterapkan pada metode, transaksi hanya berlaku untuk eksekusi metode tersebut. Ketika diterapkan pada kelas, transaksi berlaku untuk semua metode dalam kelas tersebut.|
|@Autowired|digunakan untuk memberi tahu Spring bahwa Anda ingin Spring Framework melakukan injeksi dependensi pada properti, konstruktor, atau metode tertentu.|
|@RestController|anotasi khusus yang digunakan dalam Spring Framework untuk menandai kelas sebagai pengontrol RESTful. Ini menggabungkan anotasi @Controller dan @ResponseBody, yang berarti metode di kelas tersebut akan menghasilkan data langsung sebagai respons HTTP tanpa perlu memanipulasi objek ModelAndView.|
|@RequestMapping|Ini digunakan untuk memetakan permintaan HTTP ke metode tertentu dalam pengontrol Spring. Atau untuk menentukan endpoint|
|@ResponseBody|menandai metode yang menghasilkan data yang akan dikirimkan langsung sebagai respons HTTP, tanpa perlu melalui proses penanganan tampilan atau tampilan lainnya.|
|@Controller|Ini menunjukkan kepada Spring bahwa kelas tersebut bertanggung jawab untuk menangani permintaan HTTP dari klien dan memberikan respons yang sesuai.|
|@PostMapping|Anotasi ini digunakan untuk menandai metode yang akan menangani permintaan HTTP POST.|
|@GetMapping|Anotasi ini digunakan untuk menandai metode yang akan menangani permintaan HTTP GET.|
|@PutMapping|Anotasi ini digunakan untuk menandai metode yang akan menangani permintaan HTTP PUT / update.|
|@DeleteMapping|Anotasi ini digunakan untuk menandai metode yang akan menangani permintaan HTTP DELETE.|
|@SpringBootApplication|Anotasi ini menandai kelas utama aplikasi Spring Boot. Ini menggabungkan anotasi lain seperti @Configuration, @EnableAutoConfiguration, dan @ComponentScan|
|@RestController|Anotasi ini digunakan untuk menandai kelas sebagai pengontrol REST dalam aplikasi Spring Boot. Metode yang dianotasikan dengan @RestController akan menghasilkan data langsung ke body HTTP response.|
|@Repository|Anotasi ini digunakan untuk menandai kelas sebagai bagian dari lapisan akses data (DAO) dalam arsitektur aplikasi Spring Boot. Kelas yang dianotasikan dengan @Repository biasanya berinteraksi dengan penyimpanan data seperti database.|
|@Component|Anotasi ini adalah anotasi generik untuk menandai kelas sebagai komponen dalam aplikasi Spring Boot. Ini memungkinkan Spring untuk mendeteksi dan mengelola kelas tersebut secara otomatis.|
|@Configuration|Anotasi ini digunakan untuk menandai kelas sebagai sumber konfigurasi dalam aplikasi Spring Boot. Ini memungkinkan Anda untuk mendefinisikan konfigurasi aplikasi menggunakan metode @Bean|

## Validasi
Membuat validasi pada saat insert ke db 

-	Menambah kan dependency 
--	Validation I/O

#### Menggunakan cara sederhana
Setelah menambahkan dependency maka selanjutnya kita akan melihat data yang di kirim kan di contoh ini data yang di kirimkan adalah object `Products` 
Lalu buka di `Entity` dan tambahkan Anotasi `@NotEmpty (message = “Name is required”)`
```
@NotEmpty(message = "Name is Required")
@Column(name = "product_name", length = 100)
private String name;

@NotEmpty(message = "Description is Required")
@Column(name = "product_desription", length = 500)
private String description;
```
Setelah itu buka `controller` untuk memberitahu bahwa data tersebut perlu di validasi terelbih dahulu. Tambahkan anotasi @Valid di tempaat yang di tentukan dan menambahkan param error lalu buat kondisi contoh :
```
//save
@PostMapping
public Products create(@Valid @RequestBody Products products, Errors errors){
    if (errors.hasErrors()){
        for (ObjectError error : errors.getAllErrors()){
            System.err.println(error.getDefaultMessage());
        }
        throw new RuntimeException("Validation Error");
    }
    return productService.save(products);
}
```
>Contoh di atas hanya cara lain tetapi kita butuh mengirim pesan error bisa dikirm balik ke depan sehigga kita perlu membungkus  error validasi nya  ke dalam object atau class tertentu maka dari itu kibat folder baru benama DTO

Di dalam folder dto kita bikin class ResponData.java respon data ini adalah objet yang akan di encapulasi, jadi informasi yang akan di kirim kan ke server kalau misalnya ada informasi benar atau salah maka akan di encapulasi object nya berbnetuk ReponData ini contoh :
`ResponData.java`
```
package com.example.demoapis.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponData<T> {
    private boolean status;
    private List<String> message = new ArrayList<>();
    private T payload;

    //getter setter
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
```
>Payload di sinih akan return object yang berhasil di simpan

Setalah selesai kita balik ke controller nya dan melakukan bebrapa perubahaan 
Disinih return sudah bukan data product lagi karna bisa saja returnnya data yang berhasil di simpan atau pesan error dari validasi.
Perubahan menjadi seperti ini :
```
//save
@PostMapping
public ResponseEntity<ResponData<Products>> create(@Valid @RequestBody Products products, Errors errors){

    ResponData<Products> responseData = new ResponData<>();

    if (errors.hasErrors()){
        for (ObjectError error : errors.getAllErrors()){
            responseData.getMessage().add(error.getDefaultMessage());

        }
        responseData.setStatus(false);
        responseData.setPayload(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }
    responseData.setStatus(true);
    responseData.setPayload(productService.save(products));
    return ResponseEntity.ok(responseData);
}
```
>Ingat ini hanya handle validasi masih bisa terjadi error di `responseData.setPayload(productService.save(products));` ketika di save 

-	Log respon body ketika berhasil 
    ```
    {
    "status": true,
    "message": [],
    "payload": {
        "id": 7,
        "name": "redmi",
        "description": "Product redmi ",
        "price": "100"
    }}
>`200 OK`


- Log respon body ketika validasi     
```
{
    "status": false,
    "message": [
        "Name is Required"
    ],
    "payload": null
}
```
>`400 Bad request`

## License

AES

**Happy Learning, Happy money!**