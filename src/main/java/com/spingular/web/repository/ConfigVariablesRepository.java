package com.spingular.web.repository;
import com.spingular.web.domain.ConfigVariables;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConfigVariables entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigVariablesRepository extends JpaRepository<ConfigVariables, Long>, JpaSpecificationExecutor<ConfigVariables> {

}
