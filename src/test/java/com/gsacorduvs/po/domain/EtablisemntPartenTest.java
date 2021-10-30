package com.gsacorduvs.po.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gsacorduvs.po.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EtablisemntPartenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtablisemntParten.class);
        EtablisemntParten etablisemntParten1 = new EtablisemntParten();
        etablisemntParten1.setId(1L);
        EtablisemntParten etablisemntParten2 = new EtablisemntParten();
        etablisemntParten2.setId(etablisemntParten1.getId());
        assertThat(etablisemntParten1).isEqualTo(etablisemntParten2);
        etablisemntParten2.setId(2L);
        assertThat(etablisemntParten1).isNotEqualTo(etablisemntParten2);
        etablisemntParten1.setId(null);
        assertThat(etablisemntParten1).isNotEqualTo(etablisemntParten2);
    }
}
