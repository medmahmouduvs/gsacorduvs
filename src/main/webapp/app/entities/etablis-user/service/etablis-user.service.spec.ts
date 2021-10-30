import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEtablisUser, EtablisUser } from '../etablis-user.model';

import { EtablisUserService } from './etablis-user.service';

describe('Service Tests', () => {
  describe('EtablisUser Service', () => {
    let service: EtablisUserService;
    let httpMock: HttpTestingController;
    let elemDefault: IEtablisUser;
    let expectedResult: IEtablisUser | IEtablisUser[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EtablisUserService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nome: 'AAAAAAA',
        email: 'AAAAAAA',
        position: 'AAAAAAA',
        username: 'AAAAAAA',
        password: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EtablisUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EtablisUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EtablisUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
            email: 'BBBBBB',
            position: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EtablisUser', () => {
        const patchObject = Object.assign(
          {
            email: 'BBBBBB',
            username: 'BBBBBB',
          },
          new EtablisUser()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EtablisUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nome: 'BBBBBB',
            email: 'BBBBBB',
            position: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EtablisUser', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEtablisUserToCollectionIfMissing', () => {
        it('should add a EtablisUser to an empty array', () => {
          const etablisUser: IEtablisUser = { id: 123 };
          expectedResult = service.addEtablisUserToCollectionIfMissing([], etablisUser);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(etablisUser);
        });

        it('should not add a EtablisUser to an array that contains it', () => {
          const etablisUser: IEtablisUser = { id: 123 };
          const etablisUserCollection: IEtablisUser[] = [
            {
              ...etablisUser,
            },
            { id: 456 },
          ];
          expectedResult = service.addEtablisUserToCollectionIfMissing(etablisUserCollection, etablisUser);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EtablisUser to an array that doesn't contain it", () => {
          const etablisUser: IEtablisUser = { id: 123 };
          const etablisUserCollection: IEtablisUser[] = [{ id: 456 }];
          expectedResult = service.addEtablisUserToCollectionIfMissing(etablisUserCollection, etablisUser);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(etablisUser);
        });

        it('should add only unique EtablisUser to an array', () => {
          const etablisUserArray: IEtablisUser[] = [{ id: 123 }, { id: 456 }, { id: 95175 }];
          const etablisUserCollection: IEtablisUser[] = [{ id: 123 }];
          expectedResult = service.addEtablisUserToCollectionIfMissing(etablisUserCollection, ...etablisUserArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const etablisUser: IEtablisUser = { id: 123 };
          const etablisUser2: IEtablisUser = { id: 456 };
          expectedResult = service.addEtablisUserToCollectionIfMissing([], etablisUser, etablisUser2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(etablisUser);
          expect(expectedResult).toContain(etablisUser2);
        });

        it('should accept null and undefined values', () => {
          const etablisUser: IEtablisUser = { id: 123 };
          expectedResult = service.addEtablisUserToCollectionIfMissing([], null, etablisUser, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(etablisUser);
        });

        it('should return initial array if no EtablisUser is added', () => {
          const etablisUserCollection: IEtablisUser[] = [{ id: 123 }];
          expectedResult = service.addEtablisUserToCollectionIfMissing(etablisUserCollection, undefined, null);
          expect(expectedResult).toEqual(etablisUserCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
