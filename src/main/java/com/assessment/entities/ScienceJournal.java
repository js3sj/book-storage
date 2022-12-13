package com.assessment.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@DiscriminatorValue("SCIENCE JOURNAL")
public class ScienceJournal extends Book {

    @Min(value = 1, message = "Science index is mandatory, min value 1.")
    @Max(value = 10, message = "Science index is mandatory, max value 10")
    private byte scienceIndex;

    public ScienceJournal() {}

    public ScienceJournal(Book book, byte scienceIndex) {
        super(book);
        this.scienceIndex = scienceIndex;
    }

    public byte getScienceIndex() {
        return this.scienceIndex;
    }

    public void setScienceIndex(byte scienceIndex) {
        this.scienceIndex = scienceIndex;
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice() * scienceIndex;
    }

    @Override
    public String toString() {
        String line = super.toString();
        return line.substring(0,line.length()-1)+", science index=" + this.scienceIndex +
                '}';
    }
}
