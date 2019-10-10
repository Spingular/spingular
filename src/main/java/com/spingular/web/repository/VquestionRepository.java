package com.spingular.web.repository;
import com.spingular.web.domain.Vquestion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vquestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VquestionRepository extends JpaRepository<Vquestion, Long>, JpaSpecificationExecutor<Vquestion> {

}
