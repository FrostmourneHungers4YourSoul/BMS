package com.example.bookmanagementservice.repository;

import com.example.bookmanagementservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    void removeById(Long id);

    boolean existsByTitle(String title);
}
