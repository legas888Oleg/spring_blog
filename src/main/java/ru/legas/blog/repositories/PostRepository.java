package ru.legas.blog.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.legas.blog.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}
