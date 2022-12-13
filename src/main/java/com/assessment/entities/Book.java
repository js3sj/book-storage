package com.assessment.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "book_type", discriminatorType = DiscriminatorType.STRING)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "Author is mandatory")
    private String author;

    @Min(value = 9780000000000L, message = "Barcode starts with 978... Length must be 13")
    @Max(value = 9789999999999L, message = "Barcode max value 9789999999999")
    private long barcode;

    @Min(value = 1, message = "Quantity is mandatory, min 1")
    private long quantity;

    @Min(value = 0, message = "Price is mandatory, min 0")
    private double price;

    public Book() { }

    public Book(long id, String name, String author, long barcode, long quantity, double price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.barcode = barcode;
        this.quantity = quantity;
        this.price = price;
    }

    public Book(String name, String author, long barcode, long quantity, double price) {
        this.name = name;
        this.author = author;
        this.barcode = barcode;
        this.quantity = quantity;
        this.price = price;
    }

    public Book(Book book) {
        this.id = book.id;
        this.name = book.name;
        this.author = book.author;
        this.barcode = book.barcode;
        this.quantity = book.quantity;
        this.price = book.price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public long getBarcode() {
        return this.barcode;
    }

    public long getQuantity() {
        return this.quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public double getTotalPrice() {
        return this.quantity * this.price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + this.name + '\'' +
                ", author='" + this.author + '\'' +
                ", barcode=" + this.barcode +
                ", quantity=" + this.quantity +
                ", price=" + this.price +
                '}';
    }
}