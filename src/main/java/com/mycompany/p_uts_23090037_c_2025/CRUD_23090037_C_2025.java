package com.mycompany.p_uts_23090037_c_2025;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Scanner;

public class CRUD_23090037_C_2025 {
    static final String DB_NAME = "uts_23090037_C_2025"; // Nama database sesuai format
    static final String COLL_NAME = "coll_23090037_C_2025"; // Nama collection sesuai format

    public static void main(String[] args) {
        // Koneksi ke MongoDB lokal
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(COLL_NAME);

        Scanner scanner = new Scanner(System.in);
        int choice;

        // Menu utama
        do {
            System.out.println("\n===== MENU CRUD MONGODB =====");
            System.out.println("1. Create (Tambah Data)");
            System.out.println("2. Read (Tampilkan Data)");
            System.out.println("3. Update (Ubah Data)");
            System.out.println("4. Delete (Hapus Data)");
            System.out.println("5. Search (Cari Data)");
            System.out.println("0. Keluar");
            System.out.print("Pilihan Anda: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1 -> createData(collection, scanner); // Menambah data
                case 2 -> readData(collection); // Menampilkan data
                case 3 -> updateData(collection, scanner); // Mengubah data
                case 4 -> deleteData(collection, scanner); // Menghapus data
                case 5 -> searchData(collection, scanner); // Mencari data
                case 0 -> System.out.println("Terima kasih, program selesai.");
                default -> System.out.println("Pilihan tidak tersedia.");
            }

        } while (choice != 0);

        mongoClient.close(); // Tutup koneksi MongoDB
    }

    // Fungsi untuk menambahkan 3 data (document) dengan field berbeda
    static void createData(MongoCollection<Document> collection, Scanner scanner) {
        for (int i = 0; i < 3; i++) {
            System.out.println("Data ke-" + (i + 1));
            System.out.print("Nama: ");
            String nama = scanner.nextLine();
            System.out.print("Usia: ");
            int usia = scanner.nextInt(); scanner.nextLine();
            System.out.print("Alamat: ");
            String alamat = scanner.nextLine();

            // Membuat dokumen MongoDB
            Document doc = new Document("nama", nama)
                    .append("usia", usia)
                    .append("alamat", alamat);

            collection.insertOne(doc); // Menyimpan dokumen
            System.out.println("✅ Data berhasil ditambahkan.");
        }
    }

    // Fungsi untuk menampilkan semua data
    static void readData(MongoCollection<Document> collection) {
        System.out.println("\n📋 Semua Data:");
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            System.out.println(doc.toJson());
        }
    }

    // Fungsi untuk mengupdate isi data (value) berdasarkan nama
    static void updateData(MongoCollection<Document> collection, Scanner scanner) {
        System.out.print("Masukkan nama yang ingin diupdate: ");
        String namaLama = scanner.nextLine();
        System.out.print("Masukkan nama baru: ");
        String namaBaru = scanner.nextLine();

        // Update field 'nama'
        collection.updateOne(Filters.eq("nama", namaLama), new Document("$set", new Document("nama", namaBaru)));
        System.out.println("✅ Data berhasil diupdate.");
    }

    // Fungsi untuk menghapus dokumen berdasarkan nama
    static void deleteData(MongoCollection<Document> collection, Scanner scanner) {
        System.out.print("Masukkan nama yang ingin dihapus: ");
        String nama = scanner.nextLine();

        collection.deleteOne(Filters.eq("nama", nama));
        System.out.println("✅ Data berhasil dihapus.");
    }

    // Fungsi untuk mencari data berdasarkan kata kunci di field 'nama'
    static void searchData(MongoCollection<Document> collection, Scanner scanner) {
        System.out.print("Masukkan kata kunci pencarian nama: ");
        String keyword = scanner.nextLine();

        FindIterable<Document> results = collection.find(Filters.regex("nama", ".*" + keyword + ".*", "i"));
        System.out.println("🔍 Hasil pencarian:");
        for (Document doc : results) {
            System.out.println(doc.toJson());
        }
    }
}