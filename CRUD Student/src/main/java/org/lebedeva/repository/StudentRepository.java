package org.lebedeva.repository;

import org.lebedeva.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class StudentRepository implements org.lebedeva.repository.Repository<Student, Integer> {

    private final static List<Student> students = new CopyOnWriteArrayList<>();
    private final static AtomicInteger id = new AtomicInteger(0);

    public Integer setStudentId() {
        return id.incrementAndGet();
    }

    @Override
    public Integer save(Student data) {
        students.add(data);
        data.setId(setStudentId());
        return data.getId();
    }

    @Override
    public void update(Student data) {
        students.stream()
                .filter(s -> s.getId() == data.getId())
                .findFirst().ifPresent(student -> {
            student.setFirstName(data.getFirstName());
            student.setLastName(data.getLastName());
            student.setAge(data.getAge());
            student.setGroup(data.getGroup());
        });
    }

    @Override
    public boolean delete(Student data) {
        return students.removeIf(s -> s.getId() == data.getId());
    }

    @Override
    public List<Student> findAll() {
        return students;
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
    }
}
