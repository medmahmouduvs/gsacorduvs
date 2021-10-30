package com.gsacorduvs.po.web.rest;

import com.gsacorduvs.po.domain.EtudeAccord;
import com.gsacorduvs.po.repository.EtudeAccordRepository;
import com.gsacorduvs.po.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gsacorduvs.po.domain.EtudeAccord}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EtudeAccordResource {

    private final Logger log = LoggerFactory.getLogger(EtudeAccordResource.class);

    private static final String ENTITY_NAME = "etudeAccord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtudeAccordRepository etudeAccordRepository;

    public EtudeAccordResource(EtudeAccordRepository etudeAccordRepository) {
        this.etudeAccordRepository = etudeAccordRepository;
    }

    /**
     * {@code POST  /etude-accords} : Create a new etudeAccord.
     *
     * @param etudeAccord the etudeAccord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etudeAccord, or with status {@code 400 (Bad Request)} if the etudeAccord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etude-accords")
    public ResponseEntity<EtudeAccord> createEtudeAccord(@RequestBody EtudeAccord etudeAccord) throws URISyntaxException {
        log.debug("REST request to save EtudeAccord : {}", etudeAccord);
        if (etudeAccord.getId() != null) {
            throw new BadRequestAlertException("A new etudeAccord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtudeAccord result = etudeAccordRepository.save(etudeAccord);
        return ResponseEntity
            .created(new URI("/api/etude-accords/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etude-accords/:id} : Updates an existing etudeAccord.
     *
     * @param id the id of the etudeAccord to save.
     * @param etudeAccord the etudeAccord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etudeAccord,
     * or with status {@code 400 (Bad Request)} if the etudeAccord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etudeAccord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etude-accords/{id}")
    public ResponseEntity<EtudeAccord> updateEtudeAccord(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtudeAccord etudeAccord
    ) throws URISyntaxException {
        log.debug("REST request to update EtudeAccord : {}, {}", id, etudeAccord);
        if (etudeAccord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etudeAccord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etudeAccordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtudeAccord result = etudeAccordRepository.save(etudeAccord);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etudeAccord.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etude-accords/:id} : Partial updates given fields of an existing etudeAccord, field will ignore if it is null
     *
     * @param id the id of the etudeAccord to save.
     * @param etudeAccord the etudeAccord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etudeAccord,
     * or with status {@code 400 (Bad Request)} if the etudeAccord is not valid,
     * or with status {@code 404 (Not Found)} if the etudeAccord is not found,
     * or with status {@code 500 (Internal Server Error)} if the etudeAccord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etude-accords/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EtudeAccord> partialUpdateEtudeAccord(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtudeAccord etudeAccord
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtudeAccord partially : {}, {}", id, etudeAccord);
        if (etudeAccord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etudeAccord.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etudeAccordRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtudeAccord> result = etudeAccordRepository
            .findById(etudeAccord.getId())
            .map(
                existingEtudeAccord -> {
                    if (etudeAccord.getTitre() != null) {
                        existingEtudeAccord.setTitre(etudeAccord.getTitre());
                    }
                    if (etudeAccord.getDateEtude() != null) {
                        existingEtudeAccord.setDateEtude(etudeAccord.getDateEtude());
                    }
                    if (etudeAccord.getMotiveDirCoor() != null) {
                        existingEtudeAccord.setMotiveDirCoor(etudeAccord.getMotiveDirCoor());
                    }
                    if (etudeAccord.getSignaturereacteru() != null) {
                        existingEtudeAccord.setSignaturereacteru(etudeAccord.getSignaturereacteru());
                    }
                    if (etudeAccord.getSignatureDiircore() != null) {
                        existingEtudeAccord.setSignatureDiircore(etudeAccord.getSignatureDiircore());
                    }
                    if (etudeAccord.getSignatureChefEtab() != null) {
                        existingEtudeAccord.setSignatureChefEtab(etudeAccord.getSignatureChefEtab());
                    }

                    return existingEtudeAccord;
                }
            )
            .map(etudeAccordRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etudeAccord.getId().toString())
        );
    }

    /**
     * {@code GET  /etude-accords} : get all the etudeAccords.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etudeAccords in body.
     */
    @GetMapping("/etude-accords")
    public ResponseEntity<List<EtudeAccord>> getAllEtudeAccords(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of EtudeAccords");
        Page<EtudeAccord> page;
        if (eagerload) {
            page = etudeAccordRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = etudeAccordRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etude-accords/:id} : get the "id" etudeAccord.
     *
     * @param id the id of the etudeAccord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etudeAccord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etude-accords/{id}")
    public ResponseEntity<EtudeAccord> getEtudeAccord(@PathVariable Long id) {
        log.debug("REST request to get EtudeAccord : {}", id);
        Optional<EtudeAccord> etudeAccord = etudeAccordRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(etudeAccord);
    }

    /**
     * {@code DELETE  /etude-accords/:id} : delete the "id" etudeAccord.
     *
     * @param id the id of the etudeAccord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etude-accords/{id}")
    public ResponseEntity<Void> deleteEtudeAccord(@PathVariable Long id) {
        log.debug("REST request to delete EtudeAccord : {}", id);
        etudeAccordRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
