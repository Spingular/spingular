package com.spingular.web.repository;
import com.spingular.web.domain.Vthumb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vthumb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VthumbRepository extends JpaRepository<Vthumb, Long>, JpaSpecificationExecutor<Vthumb> {

}
