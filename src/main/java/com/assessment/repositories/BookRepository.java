package com.assessment.repositories;

import com.assessment.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<? extends Book> findByBarcode(long barcode);
    Optional<? extends Book> deleteById(long id);
}
