jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EtablisemntPartenService } from '../service/etablisemnt-parten.service';
import { IEtablisemntParten, EtablisemntParten } from '../etablisemnt-parten.model';

import { EtablisemntPartenUpdateComponent } from './etablisemnt-parten-update.component';

describe('Component Tests', () => {
  describe('EtablisemntParten Management Update Component', () => {
    let comp: EtablisemntPartenUpdateComponent;
    let fixture: ComponentFixture<EtablisemntPartenUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let etablisemntPartenService: EtablisemntPartenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EtablisemntPartenUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EtablisemntPartenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtablisemntPartenUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      etablisemntPartenService = TestBed.inject(EtablisemntPartenService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const etablisemntParten: IEtablisemntParten = { id: 456 };

        activatedRoute.data = of({ etablisemntParten });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(etablisemntParten));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtablisemntParten>>();
        const etablisemntParten = { id: 123 };
        jest.spyOn(etablisemntPartenService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etablisemntParten });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etablisemntParten }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(etablisemntPartenService.update).toHaveBeenCalledWith(etablisemntParten);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtablisemntParten>>();
        const etablisemntParten = new EtablisemntParten();
        jest.spyOn(etablisemntPartenService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etablisemntParten });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etablisemntParten }));
        saveSubject.complete();

        // THEN
        expect(etablisemntPartenService.create).toHaveBeenCalledWith(etablisemntParten);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtablisemntParten>>();
        const etablisemntParten = { id: 123 };
        jest.spyOn(etablisemntPartenService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etablisemntParten });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(etablisemntPartenService.update).toHaveBeenCalledWith(etablisemntParten);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
