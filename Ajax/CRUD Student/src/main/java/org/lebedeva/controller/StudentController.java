package org.lebedeva.controller;

import org.lebedeva.model.Group;
import org.lebedeva.model.Student;
import org.lebedeva.repository.Repository;
import org.lebedeva.validator.StudentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentValidator studentValidator;
    private final Repository<Student, Integer> studentRepository;
    private final Repository<Group, Integer> groupRepository;

    @Autowired
    public StudentController(StudentValidator studentValidator,
                             @Qualifier("studentRepository") Repository<Student, Integer> studentRepository,
                             @Qualifier("groupRepository") Repository<Group, Integer> groupRepository) {
        this.studentValidator = studentValidator;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(studentValidator);
    }

    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("students", studentRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("message");
        return "students/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("student", new Student());//for (th:object) in fields
        try {
            model.addAttribute("groups", groupRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "students/create";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("student") @Validated Student student,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            try {
                model.addAttribute("groups", groupRepository.findAll());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "students/create";
        }
        int id = 0;
        try {
            id = studentRepository.save(student);
            redirectAttributes.addFlashAttribute("message", "The student successfully saved.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Save failed.");
        }
        return "redirect:/students/info/" + id;
    }

    @GetMapping("/info/{id}")
    public String info(@PathVariable int id, Model model) {
        model.addAttribute("message");
        try {
            model.addAttribute("student", studentRepository.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "students/info";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            if (studentRepository.delete(id)) {
                redirectAttributes.addFlashAttribute("message", "The student successfully deleted.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Delete failed.");
            e.printStackTrace();
        }
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable int id, Model model) {
        try {
            model.addAttribute("student", studentRepository.findById(id));
            model.addAttribute("groups", groupRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "students/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("student") @Validated Student student,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            try {
                model.addAttribute("groups", groupRepository.findAll());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "students/edit";
        }
        try {
            studentRepository.update(student);
            redirectAttributes.addFlashAttribute("message", "The student successfully updated.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Update failed.");
            e.printStackTrace();
        }
        return "redirect:/students";
    }
}
