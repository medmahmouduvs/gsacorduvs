package com.gsacorduvs.po.repository;

import com.gsacorduvs.po.domain.EtablisUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EtablisUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtablisUserRepository extends JpaRepository<EtablisUser, Long> {}
