package app.controller;

import app.domain.Human;
import app.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("human")
public class Controller {

    private Repository repository;

    @Autowired
    public Controller(Repository repository) {
        this.repository = repository;
    }

    @GetMapping("/{name}")
    public String greeting(@PathVariable String name) {
        return "Hi!!! " + name;
    }

    @GetMapping("/{id}")
    public Human getHuman(@PathVariable Long id) {
        return repository.findHumanById(id);
    }

    @GetMapping("/all")
    public List<Human> getAll(){
        return repository.findAll();
    }

    @PostMapping("/save")
    public void saveHuman (@RequestBody Human human){
        repository.save(human);
    }
}

