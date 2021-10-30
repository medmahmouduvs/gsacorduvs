jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EtudeAccordService } from '../service/etude-accord.service';
import { IEtudeAccord, EtudeAccord } from '../etude-accord.model';
import { IEspaceAcEtEl } from 'app/entities/espace-ac-et-el/espace-ac-et-el.model';
import { EspaceAcEtElService } from 'app/entities/espace-ac-et-el/service/espace-ac-et-el.service';
import { IAccorde } from 'app/entities/accorde/accorde.model';
import { AccordeService } from 'app/entities/accorde/service/accorde.service';

import { EtudeAccordUpdateComponent } from './etude-accord-update.component';

describe('Component Tests', () => {
  describe('EtudeAccord Management Update Component', () => {
    let comp: EtudeAccordUpdateComponent;
    let fixture: ComponentFixture<EtudeAccordUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let etudeAccordService: EtudeAccordService;
    let espaceAcEtElService: EspaceAcEtElService;
    let accordeService: AccordeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EtudeAccordUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EtudeAccordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtudeAccordUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      etudeAccordService = TestBed.inject(EtudeAccordService);
      espaceAcEtElService = TestBed.inject(EspaceAcEtElService);
      accordeService = TestBed.inject(AccordeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call EspaceAcEtEl query and add missing value', () => {
        const etudeAccord: IEtudeAccord = { id: 456 };
        const espaceAcEtEl: IEspaceAcEtEl = { id: 37139 };
        etudeAccord.espaceAcEtEl = espaceAcEtEl;

        const espaceAcEtElCollection: IEspaceAcEtEl[] = [{ id: 75081 }];
        jest.spyOn(espaceAcEtElService, 'query').mockReturnValue(of(new HttpResponse({ body: espaceAcEtElCollection })));
        const additionalEspaceAcEtEls = [espaceAcEtEl];
        const expectedCollection: IEspaceAcEtEl[] = [...additionalEspaceAcEtEls, ...espaceAcEtElCollection];
        jest.spyOn(espaceAcEtElService, 'addEspaceAcEtElToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ etudeAccord });
        comp.ngOnInit();

        expect(espaceAcEtElService.query).toHaveBeenCalled();
        expect(espaceAcEtElService.addEspaceAcEtElToCollectionIfMissing).toHaveBeenCalledWith(
          espaceAcEtElCollection,
          ...additionalEspaceAcEtEls
        );
        expect(comp.espaceAcEtElsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Accorde query and add missing value', () => {
        const etudeAccord: IEtudeAccord = { id: 456 };
        const accordes: IAccorde[] = [{ id: 7329 }];
        etudeAccord.accordes = accordes;

        const accordeCollection: IAccorde[] = [{ id: 38725 }];
        jest.spyOn(accordeService, 'query').mockReturnValue(of(new HttpResponse({ body: accordeCollection })));
        const additionalAccordes = [...accordes];
        const expectedCollection: IAccorde[] = [...additionalAccordes, ...accordeCollection];
        jest.spyOn(accordeService, 'addAccordeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ etudeAccord });
        comp.ngOnInit();

        expect(accordeService.query).toHaveBeenCalled();
        expect(accordeService.addAccordeToCollectionIfMissing).toHaveBeenCalledWith(accordeCollection, ...additionalAccordes);
        expect(comp.accordesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const etudeAccord: IEtudeAccord = { id: 456 };
        const espaceAcEtEl: IEspaceAcEtEl = { id: 17190 };
        etudeAccord.espaceAcEtEl = espaceAcEtEl;
        const accordes: IAccorde = { id: 23558 };
        etudeAccord.accordes = [accordes];

        activatedRoute.data = of({ etudeAccord });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(etudeAccord));
        expect(comp.espaceAcEtElsSharedCollection).toContain(espaceAcEtEl);
        expect(comp.accordesSharedCollection).toContain(accordes);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtudeAccord>>();
        const etudeAccord = { id: 123 };
        jest.spyOn(etudeAccordService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etudeAccord });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etudeAccord }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(etudeAccordService.update).toHaveBeenCalledWith(etudeAccord);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtudeAccord>>();
        const etudeAccord = new EtudeAccord();
        jest.spyOn(etudeAccordService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etudeAccord });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etudeAccord }));
        saveSubject.complete();

        // THEN
        expect(etudeAccordService.create).toHaveBeenCalledWith(etudeAccord);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtudeAccord>>();
        const etudeAccord = { id: 123 };
        jest.spyOn(etudeAccordService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etudeAccord });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(etudeAccordService.update).toHaveBeenCalledWith(etudeAccord);
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

      describe('trackAccordeById', () => {
        it('Should return tracked Accorde primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAccordeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedAccorde', () => {
        it('Should return option if no Accorde is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedAccorde(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Accorde for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedAccorde(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Accorde is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedAccorde(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
