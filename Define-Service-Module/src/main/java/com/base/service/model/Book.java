package com.base.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity(name="book")
@Table(name="book")
@NamedQuery(name="Book.findAll", query="SELECT c FROM book c")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Column(name ="id", unique=true, nullable=false)
    private Long id;
    @Column(name ="name", nullable=false, length=11)
    private String name;
    @Column(name ="author", nullable=false, length=11)
    private String author;
    @Column(name ="year")
    private Integer year;
}
