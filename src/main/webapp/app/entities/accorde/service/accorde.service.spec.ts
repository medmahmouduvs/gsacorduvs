import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Teritoir } from 'app/entities/enumerations/teritoir.model';
import { Statueoption } from 'app/entities/enumerations/statueoption.model';
import { IAccorde, Accorde } from '../accorde.model';

import { AccordeService } from './accorde.service';

describe('Service Tests', () => {
  describe('Accorde Service', () => {
    let service: AccordeService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccorde;
    let expectedResult: IAccorde | IAccorde[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AccordeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        titre: 'AAAAAAA',
        teritornature: Teritoir.INTERNATIONAL,
        statusacord: Statueoption.PROGRAMEE,
        dateAccord: currentDate,
        signaturereacteru: false,
        signatureDiircore: false,
        signatureChefEtab: false,
        article: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateAccord: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Accorde', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAccord: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAccord: currentDate,
          },
          returnedFromService
        );

        service.create(new Accorde()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Accorde', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            titre: 'BBBBBB',
            teritornature: 'BBBBBB',
            statusacord: 'BBBBBB',
            dateAccord: currentDate.format(DATE_FORMAT),
            signaturereacteru: true,
            signatureDiircore: true,
            signatureChefEtab: true,
            article: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAccord: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Accorde', () => {
        const patchObject = Object.assign(
          {
            titre: 'BBBBBB',
            teritornature: 'BBBBBB',
            dateAccord: currentDate.format(DATE_FORMAT),
            signaturereacteru: true,
            article: 'BBBBBB',
          },
          new Accorde()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateAccord: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Accorde', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            titre: 'BBBBBB',
            teritornature: 'BBBBBB',
            statusacord: 'BBBBBB',
            dateAccord: currentDate.format(DATE_FORMAT),
            signaturereacteru: true,
            signatureDiircore: true,
            signatureChefEtab: true,
            article: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAccord: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Accorde', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAccordeToCollectionIfMissing', () => {
        it('should add a Accorde to an empty array', () => {
          const accorde: IAccorde = { id: 123 };
          expectedResult = service.addAccordeToCollectionIfMissing([], accorde);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accorde);
        });

        it('should not add a Accorde to an array that contains it', () => {
          const accorde: IAccorde = { id: 123 };
          const accordeCollection: IAccorde[] = [
            {
              ...accorde,
            },
            { id: 456 },
          ];
          expectedResult = service.addAccordeToCollectionIfMissing(accordeCollection, accorde);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Accorde to an array that doesn't contain it", () => {
          const accorde: IAccorde = { id: 123 };
          const accordeCollection: IAccorde[] = [{ id: 456 }];
          expectedResult = service.addAccordeToCollectionIfMissing(accordeCollection, accorde);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accorde);
        });

        it('should add only unique Accorde to an array', () => {
          const accordeArray: IAccorde[] = [{ id: 123 }, { id: 456 }, { id: 43369 }];
          const accordeCollection: IAccorde[] = [{ id: 123 }];
          expectedResult = service.addAccordeToCollectionIfMissing(accordeCollection, ...accordeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const accorde: IAccorde = { id: 123 };
          const accorde2: IAccorde = { id: 456 };
          expectedResult = service.addAccordeToCollectionIfMissing([], accorde, accorde2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accorde);
          expect(expectedResult).toContain(accorde2);
        });

        it('should accept null and undefined values', () => {
          const accorde: IAccorde = { id: 123 };
          expectedResult = service.addAccordeToCollectionIfMissing([], null, accorde, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accorde);
        });

        it('should return initial array if no Accorde is added', () => {
          const accordeCollection: IAccorde[] = [{ id: 123 }];
          expectedResult = service.addAccordeToCollectionIfMissing(accordeCollection, undefined, null);
          expect(expectedResult).toEqual(accordeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
