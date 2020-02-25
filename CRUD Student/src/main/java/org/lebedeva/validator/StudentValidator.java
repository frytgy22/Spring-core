package org.lebedeva.validator;

import org.lebedeva.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class StudentValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Student.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "age.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "group.id", "group.empty");

        Pattern pattern = Pattern.compile("[1-9][0-9]?", Pattern.CASE_INSENSITIVE);

        Student student = (Student) o;
        String age = String.valueOf(student.getAge());

        if (!(pattern.matcher(age).matches())) {
            errors.rejectValue("age", "age.invalid");
        }
    }
}
