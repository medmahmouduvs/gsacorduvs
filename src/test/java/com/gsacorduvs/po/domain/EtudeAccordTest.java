package com.gsacorduvs.po.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gsacorduvs.po.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EtudeAccordTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtudeAccord.class);
        EtudeAccord etudeAccord1 = new EtudeAccord();
        etudeAccord1.setId(1L);
        EtudeAccord etudeAccord2 = new EtudeAccord();
        etudeAccord2.setId(etudeAccord1.getId());
        assertThat(etudeAccord1).isEqualTo(etudeAccord2);
        etudeAccord2.setId(2L);
        assertThat(etudeAccord1).isNotEqualTo(etudeAccord2);
        etudeAccord1.setId(null);
        assertThat(etudeAccord1).isNotEqualTo(etudeAccord2);
    }
}
