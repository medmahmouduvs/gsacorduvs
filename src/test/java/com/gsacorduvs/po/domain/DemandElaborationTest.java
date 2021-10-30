package com.gsacorduvs.po.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gsacorduvs.po.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandElaborationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandElaboration.class);
        DemandElaboration demandElaboration1 = new DemandElaboration();
        demandElaboration1.setId(1L);
        DemandElaboration demandElaboration2 = new DemandElaboration();
        demandElaboration2.setId(demandElaboration1.getId());
        assertThat(demandElaboration1).isEqualTo(demandElaboration2);
        demandElaboration2.setId(2L);
        assertThat(demandElaboration1).isNotEqualTo(demandElaboration2);
        demandElaboration1.setId(null);
        assertThat(demandElaboration1).isNotEqualTo(demandElaboration2);
    }
}
