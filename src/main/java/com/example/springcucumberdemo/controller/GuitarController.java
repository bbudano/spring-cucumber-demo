package com.example.springcucumberdemo.controller;

import com.example.springcucumberdemo.model.Guitar;
import com.example.springcucumberdemo.service.GuitarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/guitars")
@RequiredArgsConstructor
public class GuitarController {

    private final GuitarService guitarService;

    @PostMapping
    Guitar createGuitar(@RequestBody Guitar guitar) {
        return guitarService.createGuitar(guitar);
    }

    @GetMapping
    Iterable<Guitar> getGuitars() {
        return guitarService.getGuitars();
    }

    @GetMapping("/{id}")
    Guitar getGuitar(@PathVariable Long id) {
        return guitarService.getGuitar(id);
    }

    @DeleteMapping("/{id}")
    void deleteGuitar(@PathVariable Long id) {
        guitarService.deleteGuitar(id);
    }

}
