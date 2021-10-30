package com.gsacorduvs.po.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gsacorduvs.po.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccordeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accorde.class);
        Accorde accorde1 = new Accorde();
        accorde1.setId(1L);
        Accorde accorde2 = new Accorde();
        accorde2.setId(accorde1.getId());
        assertThat(accorde1).isEqualTo(accorde2);
        accorde2.setId(2L);
        assertThat(accorde1).isNotEqualTo(accorde2);
        accorde1.setId(null);
        assertThat(accorde1).isNotEqualTo(accorde2);
    }
}
