package com.spingular.web.repository;
import com.spingular.web.domain.Appphoto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Appphoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppphotoRepository extends JpaRepository<Appphoto, Long>, JpaSpecificationExecutor<Appphoto> {

}
