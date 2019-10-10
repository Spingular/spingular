package com.spingular.web.repository;
import com.spingular.web.domain.Proposal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Proposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long>, JpaSpecificationExecutor<Proposal> {

}
