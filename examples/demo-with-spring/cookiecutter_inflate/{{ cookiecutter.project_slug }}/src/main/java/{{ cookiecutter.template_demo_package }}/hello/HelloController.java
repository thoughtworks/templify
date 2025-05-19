package {{ cookiecutter.template_demo_package }}.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("{{ cookiecutter.get_mapping }}")
    public String hello() {
        return "{{ cookiecutter.return_get }}";
    }
}
