package com.example.springcucumberdemo.repository;

import com.example.springcucumberdemo.model.Guitar;
import org.springframework.data.repository.CrudRepository;

public interface GuitarRepository extends CrudRepository<Guitar, Long> {
}
