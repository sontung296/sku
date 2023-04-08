package com.base.service.repository;

import com.base.service.model.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("BookRepository")
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll();
}
