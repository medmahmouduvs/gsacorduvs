package com.gsacorduvs.po.repository;

import com.gsacorduvs.po.domain.EtablisemntParten;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EtablisemntParten entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtablisemntPartenRepository extends JpaRepository<EtablisemntParten, Long> {}
