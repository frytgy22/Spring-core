package org.lebedeva.validator;

import org.lebedeva.model.Group;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GroupValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Group.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "group.empty");
    }
}
