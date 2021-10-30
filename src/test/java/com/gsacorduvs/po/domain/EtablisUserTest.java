package com.gsacorduvs.po.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gsacorduvs.po.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EtablisUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtablisUser.class);
        EtablisUser etablisUser1 = new EtablisUser();
        etablisUser1.setId(1L);
        EtablisUser etablisUser2 = new EtablisUser();
        etablisUser2.setId(etablisUser1.getId());
        assertThat(etablisUser1).isEqualTo(etablisUser2);
        etablisUser2.setId(2L);
        assertThat(etablisUser1).isNotEqualTo(etablisUser2);
        etablisUser1.setId(null);
        assertThat(etablisUser1).isNotEqualTo(etablisUser2);
    }
}
