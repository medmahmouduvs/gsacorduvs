package com.gsacorduvs.po.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EtudeAccord.
 */
@Entity
@Table(name = "etude_accord")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EtudeAccord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "date_etude")
    private LocalDate dateEtude;

    @Column(name = "motive_dir_coor")
    private String motiveDirCoor;

    @Column(name = "signaturereacteru")
    private Boolean signaturereacteru;

    @Column(name = "signature_diircore")
    private Boolean signatureDiircore;

    @Column(name = "signature_chef_etab")
    private Boolean signatureChefEtab;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private EspaceAcEtEl espaceAcEtEl;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_etude_accord__accorde",
        joinColumns = @JoinColumn(name = "etude_accord_id"),
        inverseJoinColumns = @JoinColumn(name = "accorde_id")
    )
    @JsonIgnoreProperties(value = { "espaceAcEtEl", "etablisemntPartens", "etudeAccords" }, allowSetters = true)
    private Set<Accorde> accordes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtudeAccord id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitre() {
        return this.titre;
    }

    public EtudeAccord titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDateEtude() {
        return this.dateEtude;
    }

    public EtudeAccord dateEtude(LocalDate dateEtude) {
        this.dateEtude = dateEtude;
        return this;
    }

    public void setDateEtude(LocalDate dateEtude) {
        this.dateEtude = dateEtude;
    }

    public String getMotiveDirCoor() {
        return this.motiveDirCoor;
    }

    public EtudeAccord motiveDirCoor(String motiveDirCoor) {
        this.motiveDirCoor = motiveDirCoor;
        return this;
    }

    public void setMotiveDirCoor(String motiveDirCoor) {
        this.motiveDirCoor = motiveDirCoor;
    }

    public Boolean getSignaturereacteru() {
        return this.signaturereacteru;
    }

    public EtudeAccord signaturereacteru(Boolean signaturereacteru) {
        this.signaturereacteru = signaturereacteru;
        return this;
    }

    public void setSignaturereacteru(Boolean signaturereacteru) {
        this.signaturereacteru = signaturereacteru;
    }

    public Boolean getSignatureDiircore() {
        return this.signatureDiircore;
    }

    public EtudeAccord signatureDiircore(Boolean signatureDiircore) {
        this.signatureDiircore = signatureDiircore;
        return this;
    }

    public void setSignatureDiircore(Boolean signatureDiircore) {
        this.signatureDiircore = signatureDiircore;
    }

    public Boolean getSignatureChefEtab() {
        return this.signatureChefEtab;
    }

    public EtudeAccord signatureChefEtab(Boolean signatureChefEtab) {
        this.signatureChefEtab = signatureChefEtab;
        return this;
    }

    public void setSignatureChefEtab(Boolean signatureChefEtab) {
        this.signatureChefEtab = signatureChefEtab;
    }

    public EspaceAcEtEl getEspaceAcEtEl() {
        return this.espaceAcEtEl;
    }

    public EtudeAccord espaceAcEtEl(EspaceAcEtEl espaceAcEtEl) {
        this.setEspaceAcEtEl(espaceAcEtEl);
        return this;
    }

    public void setEspaceAcEtEl(EspaceAcEtEl espaceAcEtEl) {
        this.espaceAcEtEl = espaceAcEtEl;
    }

    public Set<Accorde> getAccordes() {
        return this.accordes;
    }

    public EtudeAccord accordes(Set<Accorde> accordes) {
        this.setAccordes(accordes);
        return this;
    }

    public EtudeAccord addAccorde(Accorde accorde) {
        this.accordes.add(accorde);
        accorde.getEtudeAccords().add(this);
        return this;
    }

    public EtudeAccord removeAccorde(Accorde accorde) {
        this.accordes.remove(accorde);
        accorde.getEtudeAccords().remove(this);
        return this;
    }

    public void setAccordes(Set<Accorde> accordes) {
        this.accordes = accordes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtudeAccord)) {
            return false;
        }
        return id != null && id.equals(((EtudeAccord) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtudeAccord{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", dateEtude='" + getDateEtude() + "'" +
            ", motiveDirCoor='" + getMotiveDirCoor() + "'" +
            ", signaturereacteru='" + getSignaturereacteru() + "'" +
            ", signatureDiircore='" + getSignatureDiircore() + "'" +
            ", signatureChefEtab='" + getSignatureChefEtab() + "'" +
            "}";
    }
}
