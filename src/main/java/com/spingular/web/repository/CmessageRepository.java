package com.spingular.web.repository;
import com.spingular.web.domain.Cmessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cmessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmessageRepository extends JpaRepository<Cmessage, Long>, JpaSpecificationExecutor<Cmessage> {

}
