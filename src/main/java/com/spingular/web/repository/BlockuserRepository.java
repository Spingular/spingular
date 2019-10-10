package com.spingular.web.repository;
import com.spingular.web.domain.Blockuser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Blockuser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlockuserRepository extends JpaRepository<Blockuser, Long>, JpaSpecificationExecutor<Blockuser> {

}
