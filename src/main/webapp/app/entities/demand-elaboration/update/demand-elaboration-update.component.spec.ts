jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DemandElaborationService } from '../service/demand-elaboration.service';
import { IDemandElaboration, DemandElaboration } from '../demand-elaboration.model';
import { IEspaceAcEtEl } from 'app/entities/espace-ac-et-el/espace-ac-et-el.model';
import { EspaceAcEtElService } from 'app/entities/espace-ac-et-el/service/espace-ac-et-el.service';
import { IEtablisemntParten } from 'app/entities/etablisemnt-parten/etablisemnt-parten.model';
import { EtablisemntPartenService } from 'app/entities/etablisemnt-parten/service/etablisemnt-parten.service';

import { DemandElaborationUpdateComponent } from './demand-elaboration-update.component';

describe('Component Tests', () => {
  describe('DemandElaboration Management Update Component', () => {
    let comp: DemandElaborationUpdateComponent;
    let fixture: ComponentFixture<DemandElaborationUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let demandElaborationService: DemandElaborationService;
    let espaceAcEtElService: EspaceAcEtElService;
    let etablisemntPartenService: EtablisemntPartenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DemandElaborationUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DemandElaborationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DemandElaborationUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      demandElaborationService = TestBed.inject(DemandElaborationService);
      espaceAcEtElService = TestBed.inject(EspaceAcEtElService);
      etablisemntPartenService = TestBed.inject(EtablisemntPartenService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call EspaceAcEtEl query and add missing value', () => {
        const demandElaboration: IDemandElaboration = { id: 456 };
        const espaceAcEtEl: IEspaceAcEtEl = { id: 35071 };
        demandElaboration.espaceAcEtEl = espaceAcEtEl;

        const espaceAcEtElCollection: IEspaceAcEtEl[] = [{ id: 42814 }];
        jest.spyOn(espaceAcEtElService, 'query').mockReturnValue(of(new HttpResponse({ body: espaceAcEtElCollection })));
        const additionalEspaceAcEtEls = [espaceAcEtEl];
        const expectedCollection: IEspaceAcEtEl[] = [...additionalEspaceAcEtEls, ...espaceAcEtElCollection];
        jest.spyOn(espaceAcEtElService, 'addEspaceAcEtElToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ demandElaboration });
        comp.ngOnInit();

        expect(espaceAcEtElService.query).toHaveBeenCalled();
        expect(espaceAcEtElService.addEspaceAcEtElToCollectionIfMissing).toHaveBeenCalledWith(
          espaceAcEtElCollection,
          ...additionalEspaceAcEtEls
        );
        expect(comp.espaceAcEtElsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call EtablisemntParten query and add missing value', () => {
        const demandElaboration: IDemandElaboration = { id: 456 };
        const etablisemntPartens: IEtablisemntParten[] = [{ id: 24657 }];
        demandElaboration.etablisemntPartens = etablisemntPartens;

        const etablisemntPartenCollection: IEtablisemntParten[] = [{ id: 4445 }];
        jest.spyOn(etablisemntPartenService, 'query').mockReturnValue(of(new HttpResponse({ body: etablisemntPartenCollection })));
        const additionalEtablisemntPartens = [...etablisemntPartens];
        const expectedCollection: IEtablisemntParten[] = [...additionalEtablisemntPartens, ...etablisemntPartenCollection];
        jest.spyOn(etablisemntPartenService, 'addEtablisemntPartenToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ demandElaboration });
        comp.ngOnInit();

        expect(etablisemntPartenService.query).toHaveBeenCalled();
        expect(etablisemntPartenService.addEtablisemntPartenToCollectionIfMissing).toHaveBeenCalledWith(
          etablisemntPartenCollection,
          ...additionalEtablisemntPartens
        );
        expect(comp.etablisemntPartensSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const demandElaboration: IDemandElaboration = { id: 456 };
        const espaceAcEtEl: IEspaceAcEtEl = { id: 42964 };
        demandElaboration.espaceAcEtEl = espaceAcEtEl;
        const etablisemntPartens: IEtablisemntParten = { id: 23251 };
        demandElaboration.etablisemntPartens = [etablisemntPartens];

        activatedRoute.data = of({ demandElaboration });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(demandElaboration));
        expect(comp.espaceAcEtElsSharedCollection).toContain(espaceAcEtEl);
        expect(comp.etablisemntPartensSharedCollection).toContain(etablisemntPartens);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandElaboration>>();
        const demandElaboration = { id: 123 };
        jest.spyOn(demandElaborationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandElaboration });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandElaboration }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(demandElaborationService.update).toHaveBeenCalledWith(demandElaboration);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandElaboration>>();
        const demandElaboration = new DemandElaboration();
        jest.spyOn(demandElaborationService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandElaboration });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: demandElaboration }));
        saveSubject.complete();

        // THEN
        expect(demandElaborationService.create).toHaveBeenCalledWith(demandElaboration);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DemandElaboration>>();
        const demandElaboration = { id: 123 };
        jest.spyOn(demandElaborationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ demandElaboration });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(demandElaborationService.update).toHaveBeenCalledWith(demandElaboration);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackEspaceAcEtElById', () => {
        it('Should return tracked EspaceAcEtEl primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEspaceAcEtElById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEtablisemntPartenById', () => {
        it('Should return tracked EtablisemntParten primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEtablisemntPartenById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedEtablisemntParten', () => {
        it('Should return option if no EtablisemntParten is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedEtablisemntParten(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected EtablisemntParten for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedEtablisemntParten(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this EtablisemntParten is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedEtablisemntParten(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
