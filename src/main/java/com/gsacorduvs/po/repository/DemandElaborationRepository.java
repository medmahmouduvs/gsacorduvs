package com.gsacorduvs.po.repository;

import com.gsacorduvs.po.domain.DemandElaboration;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemandElaboration entity.
 */
@Repository
public interface DemandElaborationRepository extends JpaRepository<DemandElaboration, Long> {
    @Query(
        value = "select distinct demandElaboration from DemandElaboration demandElaboration left join fetch demandElaboration.etablisemntPartens",
        countQuery = "select count(distinct demandElaboration) from DemandElaboration demandElaboration"
    )
    Page<DemandElaboration> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct demandElaboration from DemandElaboration demandElaboration left join fetch demandElaboration.etablisemntPartens"
    )
    List<DemandElaboration> findAllWithEagerRelationships();

    @Query(
        "select demandElaboration from DemandElaboration demandElaboration left join fetch demandElaboration.etablisemntPartens where demandElaboration.id =:id"
    )
    Optional<DemandElaboration> findOneWithEagerRelationships(@Param("id") Long id);
}
