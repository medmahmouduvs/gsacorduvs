jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AccordeService } from '../service/accorde.service';
import { IAccorde, Accorde } from '../accorde.model';
import { IEspaceAcEtEl } from 'app/entities/espace-ac-et-el/espace-ac-et-el.model';
import { EspaceAcEtElService } from 'app/entities/espace-ac-et-el/service/espace-ac-et-el.service';
import { IEtablisemntParten } from 'app/entities/etablisemnt-parten/etablisemnt-parten.model';
import { EtablisemntPartenService } from 'app/entities/etablisemnt-parten/service/etablisemnt-parten.service';

import { AccordeUpdateComponent } from './accorde-update.component';

describe('Component Tests', () => {
  describe('Accorde Management Update Component', () => {
    let comp: AccordeUpdateComponent;
    let fixture: ComponentFixture<AccordeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let accordeService: AccordeService;
    let espaceAcEtElService: EspaceAcEtElService;
    let etablisemntPartenService: EtablisemntPartenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AccordeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AccordeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccordeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      accordeService = TestBed.inject(AccordeService);
      espaceAcEtElService = TestBed.inject(EspaceAcEtElService);
      etablisemntPartenService = TestBed.inject(EtablisemntPartenService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call EspaceAcEtEl query and add missing value', () => {
        const accorde: IAccorde = { id: 456 };
        const espaceAcEtEl: IEspaceAcEtEl = { id: 97586 };
        accorde.espaceAcEtEl = espaceAcEtEl;

        const espaceAcEtElCollection: IEspaceAcEtEl[] = [{ id: 3103 }];
        jest.spyOn(espaceAcEtElService, 'query').mockReturnValue(of(new HttpResponse({ body: espaceAcEtElCollection })));
        const additionalEspaceAcEtEls = [espaceAcEtEl];
        const expectedCollection: IEspaceAcEtEl[] = [...additionalEspaceAcEtEls, ...espaceAcEtElCollection];
        jest.spyOn(espaceAcEtElService, 'addEspaceAcEtElToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ accorde });
        comp.ngOnInit();

        expect(espaceAcEtElService.query).toHaveBeenCalled();
        expect(espaceAcEtElService.addEspaceAcEtElToCollectionIfMissing).toHaveBeenCalledWith(
          espaceAcEtElCollection,
          ...additionalEspaceAcEtEls
        );
        expect(comp.espaceAcEtElsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call EtablisemntParten query and add missing value', () => {
        const accorde: IAccorde = { id: 456 };
        const etablisemntPartens: IEtablisemntParten[] = [{ id: 81794 }];
        accorde.etablisemntPartens = etablisemntPartens;

        const etablisemntPartenCollection: IEtablisemntParten[] = [{ id: 70539 }];
        jest.spyOn(etablisemntPartenService, 'query').mockReturnValue(of(new HttpResponse({ body: etablisemntPartenCollection })));
        const additionalEtablisemntPartens = [...etablisemntPartens];
        const expectedCollection: IEtablisemntParten[] = [...additionalEtablisemntPartens, ...etablisemntPartenCollection];
        jest.spyOn(etablisemntPartenService, 'addEtablisemntPartenToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ accorde });
        comp.ngOnInit();

        expect(etablisemntPartenService.query).toHaveBeenCalled();
        expect(etablisemntPartenService.addEtablisemntPartenToCollectionIfMissing).toHaveBeenCalledWith(
          etablisemntPartenCollection,
          ...additionalEtablisemntPartens
        );
        expect(comp.etablisemntPartensSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const accorde: IAccorde = { id: 456 };
        const espaceAcEtEl: IEspaceAcEtEl = { id: 29370 };
        accorde.espaceAcEtEl = espaceAcEtEl;
        const etablisemntPartens: IEtablisemntParten = { id: 58760 };
        accorde.etablisemntPartens = [etablisemntPartens];

        activatedRoute.data = of({ accorde });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(accorde));
        expect(comp.espaceAcEtElsSharedCollection).toContain(espaceAcEtEl);
        expect(comp.etablisemntPartensSharedCollection).toContain(etablisemntPartens);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Accorde>>();
        const accorde = { id: 123 };
        jest.spyOn(accordeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ accorde });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accorde }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(accordeService.update).toHaveBeenCalledWith(accorde);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Accorde>>();
        const accorde = new Accorde();
        jest.spyOn(accordeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ accorde });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accorde }));
        saveSubject.complete();

        // THEN
        expect(accordeService.create).toHaveBeenCalledWith(accorde);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Accorde>>();
        const accorde = { id: 123 };
        jest.spyOn(accordeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ accorde });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(accordeService.update).toHaveBeenCalledWith(accorde);
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
