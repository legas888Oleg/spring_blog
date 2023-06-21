package ru.legas.blog.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.legas.blog.models.Post;
import ru.legas.blog.repositories.PostRepository;

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
}
