package com.gsacorduvs.po.repository;

import com.gsacorduvs.po.domain.Accorde;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Accorde entity.
 */
@Repository
public interface AccordeRepository extends JpaRepository<Accorde, Long> {
    @Query(
        value = "select distinct accorde from Accorde accorde left join fetch accorde.etablisemntPartens",
        countQuery = "select count(distinct accorde) from Accorde accorde"
    )
    Page<Accorde> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct accorde from Accorde accorde left join fetch accorde.etablisemntPartens")
    List<Accorde> findAllWithEagerRelationships();

    @Query("select accorde from Accorde accorde left join fetch accorde.etablisemntPartens where accorde.id =:id")
    Optional<Accorde> findOneWithEagerRelationships(@Param("id") Long id);
}
