package com.gsacorduvs.po.web.rest;

import com.gsacorduvs.po.domain.DemandElaboration;
import com.gsacorduvs.po.repository.DemandElaborationRepository;
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
 * REST controller for managing {@link com.gsacorduvs.po.domain.DemandElaboration}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DemandElaborationResource {

    private final Logger log = LoggerFactory.getLogger(DemandElaborationResource.class);

    private static final String ENTITY_NAME = "demandElaboration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandElaborationRepository demandElaborationRepository;

    public DemandElaborationResource(DemandElaborationRepository demandElaborationRepository) {
        this.demandElaborationRepository = demandElaborationRepository;
    }

    /**
     * {@code POST  /demand-elaborations} : Create a new demandElaboration.
     *
     * @param demandElaboration the demandElaboration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandElaboration, or with status {@code 400 (Bad Request)} if the demandElaboration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demand-elaborations")
    public ResponseEntity<DemandElaboration> createDemandElaboration(@RequestBody DemandElaboration demandElaboration)
        throws URISyntaxException {
        log.debug("REST request to save DemandElaboration : {}", demandElaboration);
        if (demandElaboration.getId() != null) {
            throw new BadRequestAlertException("A new demandElaboration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandElaboration result = demandElaborationRepository.save(demandElaboration);
        return ResponseEntity
            .created(new URI("/api/demand-elaborations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demand-elaborations/:id} : Updates an existing demandElaboration.
     *
     * @param id the id of the demandElaboration to save.
     * @param demandElaboration the demandElaboration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandElaboration,
     * or with status {@code 400 (Bad Request)} if the demandElaboration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandElaboration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demand-elaborations/{id}")
    public ResponseEntity<DemandElaboration> updateDemandElaboration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandElaboration demandElaboration
    ) throws URISyntaxException {
        log.debug("REST request to update DemandElaboration : {}, {}", id, demandElaboration);
        if (demandElaboration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandElaboration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandElaborationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandElaboration result = demandElaborationRepository.save(demandElaboration);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandElaboration.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demand-elaborations/:id} : Partial updates given fields of an existing demandElaboration, field will ignore if it is null
     *
     * @param id the id of the demandElaboration to save.
     * @param demandElaboration the demandElaboration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandElaboration,
     * or with status {@code 400 (Bad Request)} if the demandElaboration is not valid,
     * or with status {@code 404 (Not Found)} if the demandElaboration is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandElaboration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demand-elaborations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DemandElaboration> partialUpdateDemandElaboration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandElaboration demandElaboration
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandElaboration partially : {}, {}", id, demandElaboration);
        if (demandElaboration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandElaboration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandElaborationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandElaboration> result = demandElaborationRepository
            .findById(demandElaboration.getId())
            .map(
                existingDemandElaboration -> {
                    if (demandElaboration.getTypeAccord() != null) {
                        existingDemandElaboration.setTypeAccord(demandElaboration.getTypeAccord());
                    }
                    if (demandElaboration.getTitreDemand() != null) {
                        existingDemandElaboration.setTitreDemand(demandElaboration.getTitreDemand());
                    }
                    if (demandElaboration.getDateDeman() != null) {
                        existingDemandElaboration.setDateDeman(demandElaboration.getDateDeman());
                    }
                    if (demandElaboration.getFormeAccord() != null) {
                        existingDemandElaboration.setFormeAccord(demandElaboration.getFormeAccord());
                    }
                    if (demandElaboration.getSignaturedircoor() != null) {
                        existingDemandElaboration.setSignaturedircoor(demandElaboration.getSignaturedircoor());
                    }

                    return existingDemandElaboration;
                }
            )
            .map(demandElaborationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandElaboration.getId().toString())
        );
    }

    /**
     * {@code GET  /demand-elaborations} : get all the demandElaborations.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandElaborations in body.
     */
    @GetMapping("/demand-elaborations")
    public ResponseEntity<List<DemandElaboration>> getAllDemandElaborations(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of DemandElaborations");
        Page<DemandElaboration> page;
        if (eagerload) {
            page = demandElaborationRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = demandElaborationRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demand-elaborations/:id} : get the "id" demandElaboration.
     *
     * @param id the id of the demandElaboration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandElaboration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demand-elaborations/{id}")
    public ResponseEntity<DemandElaboration> getDemandElaboration(@PathVariable Long id) {
        log.debug("REST request to get DemandElaboration : {}", id);
        Optional<DemandElaboration> demandElaboration = demandElaborationRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(demandElaboration);
    }

    /**
     * {@code DELETE  /demand-elaborations/:id} : delete the "id" demandElaboration.
     *
     * @param id the id of the demandElaboration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demand-elaborations/{id}")
    public ResponseEntity<Void> deleteDemandElaboration(@PathVariable Long id) {
        log.debug("REST request to delete DemandElaboration : {}", id);
        demandElaborationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
