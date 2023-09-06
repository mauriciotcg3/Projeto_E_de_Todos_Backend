package com.generation.EdeTodos.controller;

import com.generation.EdeTodos.model.Cursos;
import com.generation.EdeTodos.repository.CursosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CursosController {

    @Autowired
    private CursosRepository cursosRepository;

    @GetMapping
    public ResponseEntity<List<Cursos>> getAll(){
        return ResponseEntity.ok(cursosRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cursos> getById(@PathVariable Long id){
        return cursosRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nome/{nome_curso}")
    public ResponseEntity<List<Cursos>> getByNomecurso(@PathVariable String nomecurso){
        return ResponseEntity.ok(cursosRepository.findAllByNomecursoContainingIgnoreCase(nomecurso));
    }

    @GetMapping("/instrutor/{instrutor}")
    public ResponseEntity<List<Cursos>> getByInstrutor(@PathVariable String instrutor){
        return ResponseEntity.ok(cursosRepository.findAllByInstrutorContainingIgnoreCase(instrutor));
    }

    @PostMapping
    public ResponseEntity<Cursos> post(@Valid @RequestBody Cursos cursos){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cursosRepository.save(cursos));
    }

    @PutMapping
    public ResponseEntity<Cursos> put(@Valid @RequestBody Cursos cursos){
        return cursosRepository.findById(cursos.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(cursosRepository.save(cursos)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Cursos> cursos = cursosRepository.findById(id);

        if (cursos.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        cursosRepository.deleteById(id);


    }

}