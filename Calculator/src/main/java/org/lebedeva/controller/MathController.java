package org.lebedeva.controller;

import org.lebedeva.AssistantMethods;
import org.lebedeva.model.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MathController {
    private Calculator calculator;

    @Autowired
    public MathController(Calculator calculator) {
        this.calculator = calculator;
    }

    @RequestMapping(value = ("/"), method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(required = false) String a,
                        @RequestParam(required = false) String b,
                        @RequestParam(required = false) String op) {

        if (AssistantMethods.getDouble(a).isPresent() &&
                AssistantMethods.getDouble(b).isPresent()) {

            calculator.setFirstArg(AssistantMethods.getDouble(a).get());
            calculator.setSecondArg(AssistantMethods.getDouble(b).get());
            calculator.setOperand(AssistantMethods.getArithmeticSymbol(op));
        } else {
            calculator.setOperand("");
        }

        if (calculator.getResult().isPresent()) {
            model.addAttribute("calculator", calculator);
            model.addAttribute("result", calculator.getResult().get().toString());
        } else {
            model.addAttribute("result", "Go learn Math!");
        }
        return "index";
    }
}
