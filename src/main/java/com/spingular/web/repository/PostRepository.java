package com.spingular.web.repository;
import com.spingular.web.domain.Post;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

}
