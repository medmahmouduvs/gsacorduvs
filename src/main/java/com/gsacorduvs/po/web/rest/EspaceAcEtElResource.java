package com.gsacorduvs.po.web.rest;

import com.gsacorduvs.po.domain.EspaceAcEtEl;
import com.gsacorduvs.po.repository.EspaceAcEtElRepository;
import com.gsacorduvs.po.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.gsacorduvs.po.domain.EspaceAcEtEl}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EspaceAcEtElResource {

    private final Logger log = LoggerFactory.getLogger(EspaceAcEtElResource.class);

    private static final String ENTITY_NAME = "espaceAcEtEl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspaceAcEtElRepository espaceAcEtElRepository;

    public EspaceAcEtElResource(EspaceAcEtElRepository espaceAcEtElRepository) {
        this.espaceAcEtElRepository = espaceAcEtElRepository;
    }

    /**
     * {@code POST  /espace-ac-et-els} : Create a new espaceAcEtEl.
     *
     * @param espaceAcEtEl the espaceAcEtEl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new espaceAcEtEl, or with status {@code 400 (Bad Request)} if the espaceAcEtEl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/espace-ac-et-els")
    public ResponseEntity<EspaceAcEtEl> createEspaceAcEtEl(@Valid @RequestBody EspaceAcEtEl espaceAcEtEl) throws URISyntaxException {
        log.debug("REST request to save EspaceAcEtEl : {}", espaceAcEtEl);
        if (espaceAcEtEl.getId() != null) {
            throw new BadRequestAlertException("A new espaceAcEtEl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspaceAcEtEl result = espaceAcEtElRepository.save(espaceAcEtEl);
        return ResponseEntity
            .created(new URI("/api/espace-ac-et-els/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /espace-ac-et-els/:id} : Updates an existing espaceAcEtEl.
     *
     * @param id the id of the espaceAcEtEl to save.
     * @param espaceAcEtEl the espaceAcEtEl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated espaceAcEtEl,
     * or with status {@code 400 (Bad Request)} if the espaceAcEtEl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the espaceAcEtEl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/espace-ac-et-els/{id}")
    public ResponseEntity<EspaceAcEtEl> updateEspaceAcEtEl(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EspaceAcEtEl espaceAcEtEl
    ) throws URISyntaxException {
        log.debug("REST request to update EspaceAcEtEl : {}, {}", id, espaceAcEtEl);
        if (espaceAcEtEl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, espaceAcEtEl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!espaceAcEtElRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EspaceAcEtEl result = espaceAcEtElRepository.save(espaceAcEtEl);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, espaceAcEtEl.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /espace-ac-et-els/:id} : Partial updates given fields of an existing espaceAcEtEl, field will ignore if it is null
     *
     * @param id the id of the espaceAcEtEl to save.
     * @param espaceAcEtEl the espaceAcEtEl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated espaceAcEtEl,
     * or with status {@code 400 (Bad Request)} if the espaceAcEtEl is not valid,
     * or with status {@code 404 (Not Found)} if the espaceAcEtEl is not found,
     * or with status {@code 500 (Internal Server Error)} if the espaceAcEtEl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/espace-ac-et-els/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EspaceAcEtEl> partialUpdateEspaceAcEtEl(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EspaceAcEtEl espaceAcEtEl
    ) throws URISyntaxException {
        log.debug("REST request to partial update EspaceAcEtEl partially : {}, {}", id, espaceAcEtEl);
        if (espaceAcEtEl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, espaceAcEtEl.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!espaceAcEtElRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EspaceAcEtEl> result = espaceAcEtElRepository
            .findById(espaceAcEtEl.getId())
            .map(
                existingEspaceAcEtEl -> {
                    if (espaceAcEtEl.getName() != null) {
                        existingEspaceAcEtEl.setName(espaceAcEtEl.getName());
                    }
                    if (espaceAcEtEl.getHandle() != null) {
                        existingEspaceAcEtEl.setHandle(espaceAcEtEl.getHandle());
                    }

                    return existingEspaceAcEtEl;
                }
            )
            .map(espaceAcEtElRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, espaceAcEtEl.getId().toString())
        );
    }

    /**
     * {@code GET  /espace-ac-et-els} : get all the espaceAcEtEls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of espaceAcEtEls in body.
     */
    @GetMapping("/espace-ac-et-els")
    public List<EspaceAcEtEl> getAllEspaceAcEtEls() {
        log.debug("REST request to get all EspaceAcEtEls");
        return espaceAcEtElRepository.findAll();
    }

    /**
     * {@code GET  /espace-ac-et-els/:id} : get the "id" espaceAcEtEl.
     *
     * @param id the id of the espaceAcEtEl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the espaceAcEtEl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/espace-ac-et-els/{id}")
    public ResponseEntity<EspaceAcEtEl> getEspaceAcEtEl(@PathVariable Long id) {
        log.debug("REST request to get EspaceAcEtEl : {}", id);
        Optional<EspaceAcEtEl> espaceAcEtEl = espaceAcEtElRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(espaceAcEtEl);
    }

    /**
     * {@code DELETE  /espace-ac-et-els/:id} : delete the "id" espaceAcEtEl.
     *
     * @param id the id of the espaceAcEtEl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/espace-ac-et-els/{id}")
    public ResponseEntity<Void> deleteEspaceAcEtEl(@PathVariable Long id) {
        log.debug("REST request to delete EspaceAcEtEl : {}", id);
        espaceAcEtElRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
