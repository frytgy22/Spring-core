package org.lebedeva.api;

import org.lebedeva.model.Group;
import org.lebedeva.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupRestController {

    private final Repository<Group, Integer> groupRepository;

    @Autowired
    public GroupRestController(@Qualifier("groupRepository") Repository<Group, Integer> groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupRepository.findAll();

        if (groups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Group> getOneGroup(@PathVariable Integer id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Group group = groupRepository.findById(id);
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Group> create(@RequestBody Group group) {
        if (group != null) {
            try {
                Integer id = groupRepository.save(group);
                Group newGroup = groupRepository.findById(id);

                return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Group> update(@RequestBody Group group) {
        if (group != null) {
            try {
                groupRepository.update(group);
                return new ResponseEntity<>(group, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Group> delete(@PathVariable Integer id) {
        if (id != null) {
            try {
                if (groupRepository.delete(id)) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
