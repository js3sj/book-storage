package com.assessment.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.Year;

@Entity
@DiscriminatorValue("ANTIQUE_BOOK")
public class AntiqueBook extends Book {

    @Min(value = 0)
    @Max(value = 1900, message = "Release year is mandatory, starting from 1900.")
    private short releaseYear;

    public AntiqueBook() {}

    public AntiqueBook(Book book, short releaseYear) {
        super(book);
        this.releaseYear = releaseYear;
    }

    public short getReleaseYear() {
        return releaseYear;
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice() * (Year.now().getValue() - this.releaseYear)/10;
    }

    @Override
    public String toString() {
        String line = super.toString();
        return line.substring(0,line.length()-1)+", release year=" + this.releaseYear +
                '}';
    }
}
