package com.gsacorduvs.po.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsacorduvs.po.domain.enumeration.TypeAccord;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandElaboration.
 */
@Entity
@Table(name = "demand_elaboration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandElaboration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_accord")
    private TypeAccord typeAccord;

    @Column(name = "titre_demand")
    private String titreDemand;

    @Column(name = "date_deman")
    private LocalDate dateDeman;

    @Column(name = "forme_accord")
    private String formeAccord;

    @Column(name = "signaturedircoor")
    private Boolean signaturedircoor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private EspaceAcEtEl espaceAcEtEl;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_demand_elaboration__etablisemnt_parten",
        joinColumns = @JoinColumn(name = "demand_elaboration_id"),
        inverseJoinColumns = @JoinColumn(name = "etablisemnt_parten_id")
    )
    @JsonIgnoreProperties(value = { "accordes", "demandElaborations" }, allowSetters = true)
    private Set<EtablisemntParten> etablisemntPartens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandElaboration id(Long id) {
        this.id = id;
        return this;
    }

    public TypeAccord getTypeAccord() {
        return this.typeAccord;
    }

    public DemandElaboration typeAccord(TypeAccord typeAccord) {
        this.typeAccord = typeAccord;
        return this;
    }

    public void setTypeAccord(TypeAccord typeAccord) {
        this.typeAccord = typeAccord;
    }

    public String getTitreDemand() {
        return this.titreDemand;
    }

    public DemandElaboration titreDemand(String titreDemand) {
        this.titreDemand = titreDemand;
        return this;
    }

    public void setTitreDemand(String titreDemand) {
        this.titreDemand = titreDemand;
    }

    public LocalDate getDateDeman() {
        return this.dateDeman;
    }

    public DemandElaboration dateDeman(LocalDate dateDeman) {
        this.dateDeman = dateDeman;
        return this;
    }

    public void setDateDeman(LocalDate dateDeman) {
        this.dateDeman = dateDeman;
    }

    public String getFormeAccord() {
        return this.formeAccord;
    }

    public DemandElaboration formeAccord(String formeAccord) {
        this.formeAccord = formeAccord;
        return this;
    }

    public void setFormeAccord(String formeAccord) {
        this.formeAccord = formeAccord;
    }

    public Boolean getSignaturedircoor() {
        return this.signaturedircoor;
    }

    public DemandElaboration signaturedircoor(Boolean signaturedircoor) {
        this.signaturedircoor = signaturedircoor;
        return this;
    }

    public void setSignaturedircoor(Boolean signaturedircoor) {
        this.signaturedircoor = signaturedircoor;
    }

    public EspaceAcEtEl getEspaceAcEtEl() {
        return this.espaceAcEtEl;
    }

    public DemandElaboration espaceAcEtEl(EspaceAcEtEl espaceAcEtEl) {
        this.setEspaceAcEtEl(espaceAcEtEl);
        return this;
    }

    public void setEspaceAcEtEl(EspaceAcEtEl espaceAcEtEl) {
        this.espaceAcEtEl = espaceAcEtEl;
    }

    public Set<EtablisemntParten> getEtablisemntPartens() {
        return this.etablisemntPartens;
    }

    public DemandElaboration etablisemntPartens(Set<EtablisemntParten> etablisemntPartens) {
        this.setEtablisemntPartens(etablisemntPartens);
        return this;
    }

    public DemandElaboration addEtablisemntParten(EtablisemntParten etablisemntParten) {
        this.etablisemntPartens.add(etablisemntParten);
        etablisemntParten.getDemandElaborations().add(this);
        return this;
    }

    public DemandElaboration removeEtablisemntParten(EtablisemntParten etablisemntParten) {
        this.etablisemntPartens.remove(etablisemntParten);
        etablisemntParten.getDemandElaborations().remove(this);
        return this;
    }

    public void setEtablisemntPartens(Set<EtablisemntParten> etablisemntPartens) {
        this.etablisemntPartens = etablisemntPartens;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandElaboration)) {
            return false;
        }
        return id != null && id.equals(((DemandElaboration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandElaboration{" +
            "id=" + getId() +
            ", typeAccord='" + getTypeAccord() + "'" +
            ", titreDemand='" + getTitreDemand() + "'" +
            ", dateDeman='" + getDateDeman() + "'" +
            ", formeAccord='" + getFormeAccord() + "'" +
            ", signaturedircoor='" + getSignaturedircoor() + "'" +
            "}";
    }
}
