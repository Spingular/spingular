package com.spingular.web.repository;
import com.spingular.web.domain.Celeb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Celeb entity.
 */
@Repository
public interface CelebRepository extends JpaRepository<Celeb, Long>, JpaSpecificationExecutor<Celeb> {

    @Query(value = "select distinct celeb from Celeb celeb left join fetch celeb.appusers",
        countQuery = "select count(distinct celeb) from Celeb celeb")
    Page<Celeb> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct celeb from Celeb celeb left join fetch celeb.appusers")
    List<Celeb> findAllWithEagerRelationships();

    @Query("select celeb from Celeb celeb left join fetch celeb.appusers where celeb.id =:id")
    Optional<Celeb> findOneWithEagerRelationships(@Param("id") Long id);

}
