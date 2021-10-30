import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEtudeAccord, EtudeAccord } from '../etude-accord.model';

import { EtudeAccordService } from './etude-accord.service';

describe('Service Tests', () => {
  describe('EtudeAccord Service', () => {
    let service: EtudeAccordService;
    let httpMock: HttpTestingController;
    let elemDefault: IEtudeAccord;
    let expectedResult: IEtudeAccord | IEtudeAccord[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EtudeAccordService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        titre: 'AAAAAAA',
        dateEtude: currentDate,
        motiveDirCoor: 'AAAAAAA',
        signaturereacteru: false,
        signatureDiircore: false,
        signatureChefEtab: false,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateEtude: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EtudeAccord', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateEtude: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtude: currentDate,
          },
          returnedFromService
        );

        service.create(new EtudeAccord()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EtudeAccord', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            titre: 'BBBBBB',
            dateEtude: currentDate.format(DATE_FORMAT),
            motiveDirCoor: 'BBBBBB',
            signaturereacteru: true,
            signatureDiircore: true,
            signatureChefEtab: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtude: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EtudeAccord', () => {
        const patchObject = Object.assign(
          {
            signaturereacteru: true,
            signatureDiircore: true,
          },
          new EtudeAccord()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateEtude: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EtudeAccord', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            titre: 'BBBBBB',
            dateEtude: currentDate.format(DATE_FORMAT),
            motiveDirCoor: 'BBBBBB',
            signaturereacteru: true,
            signatureDiircore: true,
            signatureChefEtab: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtude: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EtudeAccord', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEtudeAccordToCollectionIfMissing', () => {
        it('should add a EtudeAccord to an empty array', () => {
          const etudeAccord: IEtudeAccord = { id: 123 };
          expectedResult = service.addEtudeAccordToCollectionIfMissing([], etudeAccord);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(etudeAccord);
        });

        it('should not add a EtudeAccord to an array that contains it', () => {
          const etudeAccord: IEtudeAccord = { id: 123 };
          const etudeAccordCollection: IEtudeAccord[] = [
            {
              ...etudeAccord,
            },
            { id: 456 },
          ];
          expectedResult = service.addEtudeAccordToCollectionIfMissing(etudeAccordCollection, etudeAccord);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EtudeAccord to an array that doesn't contain it", () => {
          const etudeAccord: IEtudeAccord = { id: 123 };
          const etudeAccordCollection: IEtudeAccord[] = [{ id: 456 }];
          expectedResult = service.addEtudeAccordToCollectionIfMissing(etudeAccordCollection, etudeAccord);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(etudeAccord);
        });

        it('should add only unique EtudeAccord to an array', () => {
          const etudeAccordArray: IEtudeAccord[] = [{ id: 123 }, { id: 456 }, { id: 68327 }];
          const etudeAccordCollection: IEtudeAccord[] = [{ id: 123 }];
          expectedResult = service.addEtudeAccordToCollectionIfMissing(etudeAccordCollection, ...etudeAccordArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const etudeAccord: IEtudeAccord = { id: 123 };
          const etudeAccord2: IEtudeAccord = { id: 456 };
          expectedResult = service.addEtudeAccordToCollectionIfMissing([], etudeAccord, etudeAccord2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(etudeAccord);
          expect(expectedResult).toContain(etudeAccord2);
        });

        it('should accept null and undefined values', () => {
          const etudeAccord: IEtudeAccord = { id: 123 };
          expectedResult = service.addEtudeAccordToCollectionIfMissing([], null, etudeAccord, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(etudeAccord);
        });

        it('should return initial array if no EtudeAccord is added', () => {
          const etudeAccordCollection: IEtudeAccord[] = [{ id: 123 }];
          expectedResult = service.addEtudeAccordToCollectionIfMissing(etudeAccordCollection, undefined, null);
          expect(expectedResult).toEqual(etudeAccordCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
