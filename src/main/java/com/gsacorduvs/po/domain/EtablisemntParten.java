package com.gsacorduvs.po.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EtablisemntParten.
 */
@Entity
@Table(name = "etablisemnt_parten")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EtablisemntParten implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contry")
    private String contry;

    @Column(name = "name_etab")
    private String nameEtab;

    @Column(name = "domain")
    private String domain;

    @Column(name = "mention")
    private String mention;

    @Column(name = "representantname")
    private String representantname;

    @ManyToMany(mappedBy = "etablisemntPartens")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "espaceAcEtEl", "etablisemntPartens", "etudeAccords" }, allowSetters = true)
    private Set<Accorde> accordes = new HashSet<>();

    @ManyToMany(mappedBy = "etablisemntPartens")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "espaceAcEtEl", "etablisemntPartens" }, allowSetters = true)
    private Set<DemandElaboration> demandElaborations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtablisemntParten id(Long id) {
        this.id = id;
        return this;
    }

    public String getContry() {
        return this.contry;
    }

    public EtablisemntParten contry(String contry) {
        this.contry = contry;
        return this;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getNameEtab() {
        return this.nameEtab;
    }

    public EtablisemntParten nameEtab(String nameEtab) {
        this.nameEtab = nameEtab;
        return this;
    }

    public void setNameEtab(String nameEtab) {
        this.nameEtab = nameEtab;
    }

    public String getDomain() {
        return this.domain;
    }

    public EtablisemntParten domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMention() {
        return this.mention;
    }

    public EtablisemntParten mention(String mention) {
        this.mention = mention;
        return this;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public String getRepresentantname() {
        return this.representantname;
    }

    public EtablisemntParten representantname(String representantname) {
        this.representantname = representantname;
        return this;
    }

    public void setRepresentantname(String representantname) {
        this.representantname = representantname;
    }

    public Set<Accorde> getAccordes() {
        return this.accordes;
    }

    public EtablisemntParten accordes(Set<Accorde> accordes) {
        this.setAccordes(accordes);
        return this;
    }

    public EtablisemntParten addAccorde(Accorde accorde) {
        this.accordes.add(accorde);
        accorde.getEtablisemntPartens().add(this);
        return this;
    }

    public EtablisemntParten removeAccorde(Accorde accorde) {
        this.accordes.remove(accorde);
        accorde.getEtablisemntPartens().remove(this);
        return this;
    }

    public void setAccordes(Set<Accorde> accordes) {
        if (this.accordes != null) {
            this.accordes.forEach(i -> i.removeEtablisemntParten(this));
        }
        if (accordes != null) {
            accordes.forEach(i -> i.addEtablisemntParten(this));
        }
        this.accordes = accordes;
    }

    public Set<DemandElaboration> getDemandElaborations() {
        return this.demandElaborations;
    }

    public EtablisemntParten demandElaborations(Set<DemandElaboration> demandElaborations) {
        this.setDemandElaborations(demandElaborations);
        return this;
    }

    public EtablisemntParten addDemandElaboration(DemandElaboration demandElaboration) {
        this.demandElaborations.add(demandElaboration);
        demandElaboration.getEtablisemntPartens().add(this);
        return this;
    }

    public EtablisemntParten removeDemandElaboration(DemandElaboration demandElaboration) {
        this.demandElaborations.remove(demandElaboration);
        demandElaboration.getEtablisemntPartens().remove(this);
        return this;
    }

    public void setDemandElaborations(Set<DemandElaboration> demandElaborations) {
        if (this.demandElaborations != null) {
            this.demandElaborations.forEach(i -> i.removeEtablisemntParten(this));
        }
        if (demandElaborations != null) {
            demandElaborations.forEach(i -> i.addEtablisemntParten(this));
        }
        this.demandElaborations = demandElaborations;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtablisemntParten)) {
            return false;
        }
        return id != null && id.equals(((EtablisemntParten) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtablisemntParten{" +
            "id=" + getId() +
            ", contry='" + getContry() + "'" +
            ", nameEtab='" + getNameEtab() + "'" +
            ", domain='" + getDomain() + "'" +
            ", mention='" + getMention() + "'" +
            ", representantname='" + getRepresentantname() + "'" +
            "}";
    }
}
