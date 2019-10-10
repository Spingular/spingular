package com.spingular.web.repository;
import com.spingular.web.domain.Appprofile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Appprofile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppprofileRepository extends JpaRepository<Appprofile, Long>, JpaSpecificationExecutor<Appprofile> {

}
