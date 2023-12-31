package ru.legas.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.legas.blog.models.Post;
import ru.legas.blog.repositories.PostRepository;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class BlogController {
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            Model model){

        Post post = new Post(title, anons, full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") Long id, Model model){
        if (isExistsPostById(id)) return "redirect:/blog";

        Optional<Post> optional = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        optional.ifPresent(res::add);
        model.addAttribute("post", res);

        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") Long id, Model model){
        if (isExistsPostById(id)) return "redirect:/blog";

        Optional<Post> optional = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        optional.ifPresent(res::add);
        model.addAttribute("post", res);

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(
            @PathVariable(value = "id") Long id,
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            Model model){

        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") Long id){

        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);

        return "redirect:/blog";
    }

    private boolean isExistsPostById(Long id) {

        return !postRepository.existsById(id);
    }
}
