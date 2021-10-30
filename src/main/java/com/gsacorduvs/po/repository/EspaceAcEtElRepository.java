package com.gsacorduvs.po.repository;

import com.gsacorduvs.po.domain.EspaceAcEtEl;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EspaceAcEtEl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspaceAcEtElRepository extends JpaRepository<EspaceAcEtEl, Long> {
    @Query("select espaceAcEtEl from EspaceAcEtEl espaceAcEtEl where espaceAcEtEl.user.login = ?#{principal.username}")
    List<EspaceAcEtEl> findByUserIsCurrentUser();
}
