jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EtablisUserService } from '../service/etablis-user.service';
import { IEtablisUser, EtablisUser } from '../etablis-user.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { EtablisUserUpdateComponent } from './etablis-user-update.component';

describe('Component Tests', () => {
  describe('EtablisUser Management Update Component', () => {
    let comp: EtablisUserUpdateComponent;
    let fixture: ComponentFixture<EtablisUserUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let etablisUserService: EtablisUserService;
    let userService: UserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EtablisUserUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EtablisUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtablisUserUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      etablisUserService = TestBed.inject(EtablisUserService);
      userService = TestBed.inject(UserService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call User query and add missing value', () => {
        const etablisUser: IEtablisUser = { id: 456 };
        const internalUser: IUser = { id: 10899 };
        etablisUser.internalUser = internalUser;

        const userCollection: IUser[] = [{ id: 55323 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [internalUser];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ etablisUser });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const etablisUser: IEtablisUser = { id: 456 };
        const internalUser: IUser = { id: 67140 };
        etablisUser.internalUser = internalUser;

        activatedRoute.data = of({ etablisUser });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(etablisUser));
        expect(comp.usersSharedCollection).toContain(internalUser);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtablisUser>>();
        const etablisUser = { id: 123 };
        jest.spyOn(etablisUserService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etablisUser });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etablisUser }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(etablisUserService.update).toHaveBeenCalledWith(etablisUser);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtablisUser>>();
        const etablisUser = new EtablisUser();
        jest.spyOn(etablisUserService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etablisUser });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etablisUser }));
        saveSubject.complete();

        // THEN
        expect(etablisUserService.create).toHaveBeenCalledWith(etablisUser);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtablisUser>>();
        const etablisUser = { id: 123 };
        jest.spyOn(etablisUserService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etablisUser });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(etablisUserService.update).toHaveBeenCalledWith(etablisUser);
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
