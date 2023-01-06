package com.assessment.services;

import com.assessment.entities.AntiqueBook;
import com.assessment.entities.Book;
import com.assessment.entities.ScienceJournal;
import com.assessment.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public <T extends Book> boolean save(T book) {
        if (bookRepository.findByBarcode(book.getBarcode()).isEmpty())
            return bookRepository.save(book) != null;
        return false;
    }

    public <T extends Book> boolean update(T book) {
        return bookRepository.save(book) != null;
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public boolean deleteById(long id) {
        return bookRepository.deleteById(id) != null;
    }

    public List<? extends Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findByBarcode(long barcode) {
        Optional<? extends Book> optional = bookRepository.findByBarcode(barcode);
        if (optional.isPresent())
            return optional.get();
        return null;
    }

    public void loadTestData() {
        this.save(new Book("Book1", "Author1", 9780000000001L, 1, 10.5));
        this.save(new Book("Book2", "Author2", 9780000000002L, 2, 2.5));
        this.save(new Book("Book3", "Author3", 9780000000003L, 4, 3.5));
        this.save(new Book("Book4", "Author4", 9780000000004L, 3, 4.5));
        this.save(new Book("Book5", "Author5", 9780000000005L, 3, 2.5));
        this.save(new AntiqueBook(
                new Book("AntiqueBook1", "Author6", 9780000000006L, 1, 2.5), (short)1550));
        this.save(new AntiqueBook(
                new Book("AntiqueBook2", "Author7", 9780000000007L, 2, 70.5), (short)1450));
        this.save(new AntiqueBook(
                new Book("AntiqueBook3", "Author8", 9780000000008L, 2, 5.5), (short)1890));
        this.save(new ScienceJournal(
                new Book("ScienceJournal7", "Author9", 9780000000009L, 1, 5.5), (byte)4));
        this.save(new ScienceJournal(
                new Book("ScienceJournal7", "Author10", 9780000000010L, 1, 5.5), (byte)1));
    }
}
