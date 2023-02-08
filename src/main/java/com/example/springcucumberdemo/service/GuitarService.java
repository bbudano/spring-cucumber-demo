package com.example.springcucumberdemo.service;

import com.example.springcucumberdemo.model.Guitar;
import com.example.springcucumberdemo.repository.GuitarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuitarService {

    private final GuitarRepository guitarRepository;

    public Guitar createGuitar(Guitar guitar) {
        return guitarRepository.save(guitar);
    }

    public Iterable<Guitar> getGuitars() {
        return guitarRepository.findAll();
    }

    public Guitar getGuitar(Long id){
        return getGuitarById(id);
    }

    public void deleteGuitar(Long id) {
        var guitar = getGuitarById(id);

        guitarRepository.delete(guitar);
    }

    private Guitar getGuitarById(Long id) {
        return guitarRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Guitar not found by id: " + id));
    }

}
