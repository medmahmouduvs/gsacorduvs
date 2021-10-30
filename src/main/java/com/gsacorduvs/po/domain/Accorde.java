package com.gsacorduvs.po.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsacorduvs.po.domain.enumeration.Statueoption;
import com.gsacorduvs.po.domain.enumeration.Teritoir;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Accorde.
 */
@Entity
@Table(name = "accorde")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Accorde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Enumerated(EnumType.STRING)
    @Column(name = "teritornature")
    private Teritoir teritornature;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusacord")
    private Statueoption statusacord;

    @Column(name = "date_accord")
    private LocalDate dateAccord;

    @Column(name = "signaturereacteru")
    private Boolean signaturereacteru;

    @Column(name = "signature_diircore")
    private Boolean signatureDiircore;

    @Column(name = "signature_chef_etab")
    private Boolean signatureChefEtab;

    @Column(name = "article")
    private String article;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private EspaceAcEtEl espaceAcEtEl;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_accorde__etablisemnt_parten",
        joinColumns = @JoinColumn(name = "accorde_id"),
        inverseJoinColumns = @JoinColumn(name = "etablisemnt_parten_id")
    )
    @JsonIgnoreProperties(value = { "accordes", "demandElaborations" }, allowSetters = true)
    private Set<EtablisemntParten> etablisemntPartens = new HashSet<>();

    @ManyToMany(mappedBy = "accordes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "espaceAcEtEl", "accordes" }, allowSetters = true)
    private Set<EtudeAccord> etudeAccords = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accorde id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitre() {
        return this.titre;
    }

    public Accorde titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Teritoir getTeritornature() {
        return this.teritornature;
    }

    public Accorde teritornature(Teritoir teritornature) {
        this.teritornature = teritornature;
        return this;
    }

    public void setTeritornature(Teritoir teritornature) {
        this.teritornature = teritornature;
    }

    public Statueoption getStatusacord() {
        return this.statusacord;
    }

    public Accorde statusacord(Statueoption statusacord) {
        this.statusacord = statusacord;
        return this;
    }

    public void setStatusacord(Statueoption statusacord) {
        this.statusacord = statusacord;
    }

    public LocalDate getDateAccord() {
        return this.dateAccord;
    }

    public Accorde dateAccord(LocalDate dateAccord) {
        this.dateAccord = dateAccord;
        return this;
    }

    public void setDateAccord(LocalDate dateAccord) {
        this.dateAccord = dateAccord;
    }

    public Boolean getSignaturereacteru() {
        return this.signaturereacteru;
    }

    public Accorde signaturereacteru(Boolean signaturereacteru) {
        this.signaturereacteru = signaturereacteru;
        return this;
    }

    public void setSignaturereacteru(Boolean signaturereacteru) {
        this.signaturereacteru = signaturereacteru;
    }

    public Boolean getSignatureDiircore() {
        return this.signatureDiircore;
    }

    public Accorde signatureDiircore(Boolean signatureDiircore) {
        this.signatureDiircore = signatureDiircore;
        return this;
    }

    public void setSignatureDiircore(Boolean signatureDiircore) {
        this.signatureDiircore = signatureDiircore;
    }

    public Boolean getSignatureChefEtab() {
        return this.signatureChefEtab;
    }

    public Accorde signatureChefEtab(Boolean signatureChefEtab) {
        this.signatureChefEtab = signatureChefEtab;
        return this;
    }

    public void setSignatureChefEtab(Boolean signatureChefEtab) {
        this.signatureChefEtab = signatureChefEtab;
    }

    public String getArticle() {
        return this.article;
    }

    public Accorde article(String article) {
        this.article = article;
        return this;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public EspaceAcEtEl getEspaceAcEtEl() {
        return this.espaceAcEtEl;
    }

    public Accorde espaceAcEtEl(EspaceAcEtEl espaceAcEtEl) {
        this.setEspaceAcEtEl(espaceAcEtEl);
        return this;
    }

    public void setEspaceAcEtEl(EspaceAcEtEl espaceAcEtEl) {
        this.espaceAcEtEl = espaceAcEtEl;
    }

    public Set<EtablisemntParten> getEtablisemntPartens() {
        return this.etablisemntPartens;
    }

    public Accorde etablisemntPartens(Set<EtablisemntParten> etablisemntPartens) {
        this.setEtablisemntPartens(etablisemntPartens);
        return this;
    }

    public Accorde addEtablisemntParten(EtablisemntParten etablisemntParten) {
        this.etablisemntPartens.add(etablisemntParten);
        etablisemntParten.getAccordes().add(this);
        return this;
    }

    public Accorde removeEtablisemntParten(EtablisemntParten etablisemntParten) {
        this.etablisemntPartens.remove(etablisemntParten);
        etablisemntParten.getAccordes().remove(this);
        return this;
    }

    public void setEtablisemntPartens(Set<EtablisemntParten> etablisemntPartens) {
        this.etablisemntPartens = etablisemntPartens;
    }

    public Set<EtudeAccord> getEtudeAccords() {
        return this.etudeAccords;
    }

    public Accorde etudeAccords(Set<EtudeAccord> etudeAccords) {
        this.setEtudeAccords(etudeAccords);
        return this;
    }

    public Accorde addEtudeAccord(EtudeAccord etudeAccord) {
        this.etudeAccords.add(etudeAccord);
        etudeAccord.getAccordes().add(this);
        return this;
    }

    public Accorde removeEtudeAccord(EtudeAccord etudeAccord) {
        this.etudeAccords.remove(etudeAccord);
        etudeAccord.getAccordes().remove(this);
        return this;
    }

    public void setEtudeAccords(Set<EtudeAccord> etudeAccords) {
        if (this.etudeAccords != null) {
            this.etudeAccords.forEach(i -> i.removeAccorde(this));
        }
        if (etudeAccords != null) {
            etudeAccords.forEach(i -> i.addAccorde(this));
        }
        this.etudeAccords = etudeAccords;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accorde)) {
            return false;
        }
        return id != null && id.equals(((Accorde) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accorde{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", teritornature='" + getTeritornature() + "'" +
            ", statusacord='" + getStatusacord() + "'" +
            ", dateAccord='" + getDateAccord() + "'" +
            ", signaturereacteru='" + getSignaturereacteru() + "'" +
            ", signatureDiircore='" + getSignatureDiircore() + "'" +
            ", signatureChefEtab='" + getSignatureChefEtab() + "'" +
            ", article='" + getArticle() + "'" +
            "}";
    }
}
