package ccrpack.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ccrpack.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
