package org.lebedeva.controller;

import org.lebedeva.model.Group;
import org.lebedeva.repository.Repository;
import org.lebedeva.validator.GroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupValidator groupValidator;
    private final Repository<Group, Integer> repository;

    @Autowired
    public GroupController(GroupValidator groupValidator,
                           @Qualifier("groupRepository") Repository<Group, Integer> repository) {
        this.groupValidator = groupValidator;
        this.repository = repository;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(groupValidator);
    }

    @GetMapping
    public String index(Model model) {
        try {
            model.addAttribute("groups", repository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("message");
        return "groups/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("group", new Group());//for (th:object) in fields
        return "groups/create";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("group") @Validated Group group,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "groups/create";
        }
        int id = 0;
        try {
            id = repository.save(group);
            redirectAttributes.addFlashAttribute("message", "The group successfully saved.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Save failed.");
        }
        return "redirect:/groups/info/" + id;
    }

    @GetMapping("/info/{id}")
    public String info(@PathVariable int id, Model model) {
        model.addAttribute("message");
        try {
            model.addAttribute("group", repository.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "groups/info";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            if (repository.delete(id)) {
                redirectAttributes.addFlashAttribute("message", "The group successfully deleted.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Delete failed. There are students in this group.");
            e.printStackTrace();
        }
        return "redirect:/groups";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable int id, Model model) {
        try {
            model.addAttribute("group", repository.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "groups/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("group") @Validated Group group,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "groups/edit";
        }
        try {
            repository.update(group);
            redirectAttributes.addFlashAttribute("message", "The group successfully updated.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Update failed.");
            e.printStackTrace();
        }
        return "redirect:/groups";
    }
}
