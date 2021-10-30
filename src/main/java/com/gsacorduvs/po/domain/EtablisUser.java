package com.gsacorduvs.po.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EtablisUser.
 */
@Entity
@Table(name = "etablis_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EtablisUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "position")
    private String position;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtablisUser id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public EtablisUser nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public EtablisUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return this.position;
    }

    public EtablisUser position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUsername() {
        return this.username;
    }

    public EtablisUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public EtablisUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public EtablisUser internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtablisUser)) {
            return false;
        }
        return id != null && id.equals(((EtablisUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtablisUser{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", position='" + getPosition() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
