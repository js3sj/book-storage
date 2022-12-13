package com.assessment.utils;

import com.assessment.entities.AntiqueBook;
import com.assessment.entities.Book;
import com.assessment.entities.ScienceJournal;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class FormAttribute {

    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @NotEmpty(message = "Author is mandatory")
    private String author;

    @Min(value = 9780000000000L, message = "Barcode starts with 978... Length must be 13.")
    @Max(value = 9789999999999L, message = "Barcode max value 9789999999999.")
    private long barcode;

    @Min(value = 1, message = "Quantity is mandatory, min 1.")
    private long quantity;

    @Min(value = 0, message = "Price is mandatory, min 0.")
    private double price;

    private Short releaseYear;

    private Byte scienceIndex;

    private double totalPrice;

    public FormAttribute() {
    }

    public <T extends Book> FormAttribute(T book) {
        this.id = book.getId();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.barcode = book.getBarcode();
        this.quantity = book.getQuantity();
        this.price = book.getPrice();

        if (book instanceof AntiqueBook) {
            this.releaseYear = ((AntiqueBook) book).getReleaseYear();
        }

        if (book instanceof ScienceJournal) {
            this.scienceIndex = ((ScienceJournal) book).getScienceIndex();
        }
    }

    public <T extends Book> T buildNewBook() {
        if ((this.releaseYear != null) && (this.scienceIndex == null)) {
            return (T) new AntiqueBook(
                    this.BuildBook(),
                    this.releaseYear
            );
        } else if ((this.releaseYear == null) && (this.scienceIndex != null)) {
            return (T) new ScienceJournal(
                    this.BuildBook(),
                    this.scienceIndex
            );
        } else
            return (T) this.BuildBook();
    }

    private Book BuildBook() {
        return new Book(
                this.id,
                this.name,
                this.author,
                this.barcode,
                this.quantity,
                this.price
        );
    }

    public List<FormAttribute> mapToFormAttributeList(List<? extends Book> listOfBook) {
        List<FormAttribute> resultList = new ArrayList<>();

        listOfBook.forEach((obj) -> {
            if (obj instanceof AntiqueBook) {
                resultList.add(new FormAttribute((AntiqueBook) obj));
            } else if (obj instanceof ScienceJournal) {
                resultList.add(new FormAttribute((ScienceJournal) obj));
            } else {
                resultList.add(new FormAttribute((Book) obj));
            }
        });

        return resultList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Short getReleaseYear() {
        return this.releaseYear;
    }

    public void setReleaseYear(Short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Byte getScienceIndex() { return this.scienceIndex; }

    public void setScienceIndex(Byte scienceIndex) {
        this.scienceIndex = scienceIndex;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getResultDescriptionForTheFirstGroup() {
        return "LIST OF ALL THE BARCODES FOR THE BOOKS IN STOCK GROUPED BY QUANTITY:";
    }

    public String getResultDescriptionForTheSecondGroup() {
        return "LIST OF ALL THE BARCODES FOR THE BOOKS IN STOCK GROUPED BY QUANTITY AND SORTED BY TOTAL PRICE:";
    }

    public boolean checkReleaseYear() {
        return ((this.releaseYear >= 0) && (this.releaseYear <= 1900));
    }

    public boolean checkScienceIndex() { return ((this.scienceIndex >= 1) && (this.scienceIndex <= 10)); }

    @Override
    public String toString() {
        return "FormAttribute{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", barcode=" + barcode +
                ", quantity=" + quantity +
                ", price=" + price +
                ", releaseYear=" + releaseYear +
                ", scienceIndex=" + scienceIndex +
                '}';
    }
}