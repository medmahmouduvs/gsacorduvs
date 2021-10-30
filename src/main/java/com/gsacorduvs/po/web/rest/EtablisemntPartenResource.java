package com.gsacorduvs.po.web.rest;

import com.gsacorduvs.po.domain.EtablisemntParten;
import com.gsacorduvs.po.repository.EtablisemntPartenRepository;
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
 * REST controller for managing {@link com.gsacorduvs.po.domain.EtablisemntParten}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EtablisemntPartenResource {

    private final Logger log = LoggerFactory.getLogger(EtablisemntPartenResource.class);

    private static final String ENTITY_NAME = "etablisemntParten";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtablisemntPartenRepository etablisemntPartenRepository;

    public EtablisemntPartenResource(EtablisemntPartenRepository etablisemntPartenRepository) {
        this.etablisemntPartenRepository = etablisemntPartenRepository;
    }

    /**
     * {@code POST  /etablisemnt-partens} : Create a new etablisemntParten.
     *
     * @param etablisemntParten the etablisemntParten to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etablisemntParten, or with status {@code 400 (Bad Request)} if the etablisemntParten has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etablisemnt-partens")
    public ResponseEntity<EtablisemntParten> createEtablisemntParten(@RequestBody EtablisemntParten etablisemntParten)
        throws URISyntaxException {
        log.debug("REST request to save EtablisemntParten : {}", etablisemntParten);
        if (etablisemntParten.getId() != null) {
            throw new BadRequestAlertException("A new etablisemntParten cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtablisemntParten result = etablisemntPartenRepository.save(etablisemntParten);
        return ResponseEntity
            .created(new URI("/api/etablisemnt-partens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etablisemnt-partens/:id} : Updates an existing etablisemntParten.
     *
     * @param id the id of the etablisemntParten to save.
     * @param etablisemntParten the etablisemntParten to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etablisemntParten,
     * or with status {@code 400 (Bad Request)} if the etablisemntParten is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etablisemntParten couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etablisemnt-partens/{id}")
    public ResponseEntity<EtablisemntParten> updateEtablisemntParten(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtablisemntParten etablisemntParten
    ) throws URISyntaxException {
        log.debug("REST request to update EtablisemntParten : {}, {}", id, etablisemntParten);
        if (etablisemntParten.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etablisemntParten.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etablisemntPartenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtablisemntParten result = etablisemntPartenRepository.save(etablisemntParten);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etablisemntParten.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etablisemnt-partens/:id} : Partial updates given fields of an existing etablisemntParten, field will ignore if it is null
     *
     * @param id the id of the etablisemntParten to save.
     * @param etablisemntParten the etablisemntParten to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etablisemntParten,
     * or with status {@code 400 (Bad Request)} if the etablisemntParten is not valid,
     * or with status {@code 404 (Not Found)} if the etablisemntParten is not found,
     * or with status {@code 500 (Internal Server Error)} if the etablisemntParten couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etablisemnt-partens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EtablisemntParten> partialUpdateEtablisemntParten(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtablisemntParten etablisemntParten
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtablisemntParten partially : {}, {}", id, etablisemntParten);
        if (etablisemntParten.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etablisemntParten.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etablisemntPartenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtablisemntParten> result = etablisemntPartenRepository
            .findById(etablisemntParten.getId())
            .map(
                existingEtablisemntParten -> {
                    if (etablisemntParten.getContry() != null) {
                        existingEtablisemntParten.setContry(etablisemntParten.getContry());
                    }
                    if (etablisemntParten.getNameEtab() != null) {
                        existingEtablisemntParten.setNameEtab(etablisemntParten.getNameEtab());
                    }
                    if (etablisemntParten.getDomain() != null) {
                        existingEtablisemntParten.setDomain(etablisemntParten.getDomain());
                    }
                    if (etablisemntParten.getMention() != null) {
                        existingEtablisemntParten.setMention(etablisemntParten.getMention());
                    }
                    if (etablisemntParten.getRepresentantname() != null) {
                        existingEtablisemntParten.setRepresentantname(etablisemntParten.getRepresentantname());
                    }

                    return existingEtablisemntParten;
                }
            )
            .map(etablisemntPartenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etablisemntParten.getId().toString())
        );
    }

    /**
     * {@code GET  /etablisemnt-partens} : get all the etablisemntPartens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etablisemntPartens in body.
     */
    @GetMapping("/etablisemnt-partens")
    public ResponseEntity<List<EtablisemntParten>> getAllEtablisemntPartens(Pageable pageable) {
        log.debug("REST request to get a page of EtablisemntPartens");
        Page<EtablisemntParten> page = etablisemntPartenRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etablisemnt-partens/:id} : get the "id" etablisemntParten.
     *
     * @param id the id of the etablisemntParten to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etablisemntParten, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etablisemnt-partens/{id}")
    public ResponseEntity<EtablisemntParten> getEtablisemntParten(@PathVariable Long id) {
        log.debug("REST request to get EtablisemntParten : {}", id);
        Optional<EtablisemntParten> etablisemntParten = etablisemntPartenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(etablisemntParten);
    }

    /**
     * {@code DELETE  /etablisemnt-partens/:id} : delete the "id" etablisemntParten.
     *
     * @param id the id of the etablisemntParten to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etablisemnt-partens/{id}")
    public ResponseEntity<Void> deleteEtablisemntParten(@PathVariable Long id) {
        log.debug("REST request to delete EtablisemntParten : {}", id);
        etablisemntPartenRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
