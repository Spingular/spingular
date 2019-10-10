package com.spingular.web.repository;
import com.spingular.web.domain.Vanswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vanswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VanswerRepository extends JpaRepository<Vanswer, Long>, JpaSpecificationExecutor<Vanswer> {

}
