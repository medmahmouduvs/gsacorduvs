package com.gsacorduvs.po.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gsacorduvs.po.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspaceAcEtElTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspaceAcEtEl.class);
        EspaceAcEtEl espaceAcEtEl1 = new EspaceAcEtEl();
        espaceAcEtEl1.setId(1L);
        EspaceAcEtEl espaceAcEtEl2 = new EspaceAcEtEl();
        espaceAcEtEl2.setId(espaceAcEtEl1.getId());
        assertThat(espaceAcEtEl1).isEqualTo(espaceAcEtEl2);
        espaceAcEtEl2.setId(2L);
        assertThat(espaceAcEtEl1).isNotEqualTo(espaceAcEtEl2);
        espaceAcEtEl1.setId(null);
        assertThat(espaceAcEtEl1).isNotEqualTo(espaceAcEtEl2);
    }
}
