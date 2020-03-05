package org.lebedeva.api;

import org.lebedeva.model.Student;
import org.lebedeva.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private final Repository<Student, Integer> studentRepository;

    @Autowired
    public StudentRestController(@Qualifier("studentRepository") Repository<Student, Integer> studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getOneStudent(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Student student = studentRepository.findById(id);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> create(@RequestBody Student student) {
        if (student != null) {
            try {
                Integer id = studentRepository.save(student);
                Student newStudent = studentRepository.findById(id);

                return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> update(@RequestBody Student student) {
        if (student != null) {
            try {
                studentRepository.update(student);
                return new ResponseEntity<>(student, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> delete(@PathVariable Integer id) {
        if (id != null) {
            try {
                if (studentRepository.delete(id)) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}




