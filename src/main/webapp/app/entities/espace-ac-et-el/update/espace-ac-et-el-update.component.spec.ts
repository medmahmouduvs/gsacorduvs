jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EspaceAcEtElService } from '../service/espace-ac-et-el.service';
import { IEspaceAcEtEl, EspaceAcEtEl } from '../espace-ac-et-el.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { EspaceAcEtElUpdateComponent } from './espace-ac-et-el-update.component';

describe('Component Tests', () => {
  describe('EspaceAcEtEl Management Update Component', () => {
    let comp: EspaceAcEtElUpdateComponent;
    let fixture: ComponentFixture<EspaceAcEtElUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let espaceAcEtElService: EspaceAcEtElService;
    let userService: UserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EspaceAcEtElUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EspaceAcEtElUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EspaceAcEtElUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      espaceAcEtElService = TestBed.inject(EspaceAcEtElService);
      userService = TestBed.inject(UserService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call User query and add missing value', () => {
        const espaceAcEtEl: IEspaceAcEtEl = { id: 456 };
        const user: IUser = { id: 72283 };
        espaceAcEtEl.user = user;

        const userCollection: IUser[] = [{ id: 54274 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [user];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ espaceAcEtEl });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const espaceAcEtEl: IEspaceAcEtEl = { id: 456 };
        const user: IUser = { id: 52016 };
        espaceAcEtEl.user = user;

        activatedRoute.data = of({ espaceAcEtEl });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(espaceAcEtEl));
        expect(comp.usersSharedCollection).toContain(user);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EspaceAcEtEl>>();
        const espaceAcEtEl = { id: 123 };
        jest.spyOn(espaceAcEtElService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ espaceAcEtEl });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: espaceAcEtEl }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(espaceAcEtElService.update).toHaveBeenCalledWith(espaceAcEtEl);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EspaceAcEtEl>>();
        const espaceAcEtEl = new EspaceAcEtEl();
        jest.spyOn(espaceAcEtElService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ espaceAcEtEl });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: espaceAcEtEl }));
        saveSubject.complete();

        // THEN
        expect(espaceAcEtElService.create).toHaveBeenCalledWith(espaceAcEtEl);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EspaceAcEtEl>>();
        const espaceAcEtEl = { id: 123 };
        jest.spyOn(espaceAcEtElService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ espaceAcEtEl });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(espaceAcEtElService.update).toHaveBeenCalledWith(espaceAcEtEl);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserById', () => {
        it('Should return tracked User primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});