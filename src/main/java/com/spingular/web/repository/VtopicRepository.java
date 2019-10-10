package com.spingular.web.repository;
import com.spingular.web.domain.Vtopic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vtopic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VtopicRepository extends JpaRepository<Vtopic, Long>, JpaSpecificationExecutor<Vtopic> {

}
