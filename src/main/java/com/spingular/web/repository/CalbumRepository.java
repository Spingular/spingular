package com.spingular.web.repository;
import com.spingular.web.domain.Calbum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Calbum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalbumRepository extends JpaRepository<Calbum, Long>, JpaSpecificationExecutor<Calbum> {

}
