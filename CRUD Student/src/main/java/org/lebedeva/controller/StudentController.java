package org.lebedeva.controller;

import org.lebedeva.model.Student;
import org.lebedeva.repository.StudentRepository;
import org.lebedeva.validator.StudentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentValidator studentValidator;
    private final StudentRepository repository;

    @Autowired
    public StudentController(StudentRepository repository, StudentValidator studentValidator) {
        this.repository = repository;
        this.studentValidator = studentValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(studentValidator);
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("students", repository.findAll());
        return "students/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("student", new Student());//for (th:object) in fields
        return "students/create";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("student") @Validated Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "students/create";
        }
        int id = repository.save(student);
        return "redirect:/students/info/" + id;
    }

    @GetMapping("/info/{id}")
    public String info(@PathVariable int id, Model model) {
        if (repository.findById(id).isPresent()) {
            model.addAttribute("student", repository.findById(id).get());
            return "students/info";
        }
        return "redirect:/error";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            Student student = repository.findById(id).get();
            return repository.delete(student) ? "redirect:/students" : "redirect:/error";
        }
        return "redirect:/error";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable int id, Model model) {
        if (repository.findById(id).isPresent()) {
            Student student = repository.findById(id).get();
            model.addAttribute("student", student);
            return "students/edit";
        }
        return "redirect:/error";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("student") @Validated Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "students/edit";
        }
        repository.update(student);
        return "redirect:/students";
    }
}
