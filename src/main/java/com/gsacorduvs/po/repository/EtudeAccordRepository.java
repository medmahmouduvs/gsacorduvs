package com.gsacorduvs.po.repository;

import com.gsacorduvs.po.domain.EtudeAccord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EtudeAccord entity.
 */
@Repository
public interface EtudeAccordRepository extends JpaRepository<EtudeAccord, Long> {
    @Query(
        value = "select distinct etudeAccord from EtudeAccord etudeAccord left join fetch etudeAccord.accordes",
        countQuery = "select count(distinct etudeAccord) from EtudeAccord etudeAccord"
    )
    Page<EtudeAccord> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct etudeAccord from EtudeAccord etudeAccord left join fetch etudeAccord.accordes")
    List<EtudeAccord> findAllWithEagerRelationships();

    @Query("select etudeAccord from EtudeAccord etudeAccord left join fetch etudeAccord.accordes where etudeAccord.id =:id")
    Optional<EtudeAccord> findOneWithEagerRelationships(@Param("id") Long id);
}
