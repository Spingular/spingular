package com.spingular.web.repository;
import com.spingular.web.domain.Community;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Community entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunityRepository extends JpaRepository<Community, Long>, JpaSpecificationExecutor<Community> {

}
