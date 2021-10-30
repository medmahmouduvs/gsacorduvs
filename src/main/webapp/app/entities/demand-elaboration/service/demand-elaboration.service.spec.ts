import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { TypeAccord } from 'app/entities/enumerations/type-accord.model';
import { IDemandElaboration, DemandElaboration } from '../demand-elaboration.model';

import { DemandElaborationService } from './demand-elaboration.service';

describe('Service Tests', () => {
  describe('DemandElaboration Service', () => {
    let service: DemandElaborationService;
    let httpMock: HttpTestingController;
    let elemDefault: IDemandElaboration;
    let expectedResult: IDemandElaboration | IDemandElaboration[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(DemandElaborationService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        typeAccord: TypeAccord.ACCORD,
        titreDemand: 'AAAAAAA',
        dateDeman: currentDate,
        formeAccord: 'AAAAAAA',
        signaturedircoor: false,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateDeman: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DemandElaboration', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateDeman: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeman: currentDate,
          },
          returnedFromService
        );

        service.create(new DemandElaboration()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DemandElaboration', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeAccord: 'BBBBBB',
            titreDemand: 'BBBBBB',
            dateDeman: currentDate.format(DATE_FORMAT),
            formeAccord: 'BBBBBB',
            signaturedircoor: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeman: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a DemandElaboration', () => {
        const patchObject = Object.assign(
          {
            formeAccord: 'BBBBBB',
          },
          new DemandElaboration()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDeman: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DemandElaboration', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeAccord: 'BBBBBB',
            titreDemand: 'BBBBBB',
            dateDeman: currentDate.format(DATE_FORMAT),
            formeAccord: 'BBBBBB',
            signaturedircoor: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeman: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DemandElaboration', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addDemandElaborationToCollectionIfMissing', () => {
        it('should add a DemandElaboration to an empty array', () => {
          const demandElaboration: IDemandElaboration = { id: 123 };
          expectedResult = service.addDemandElaborationToCollectionIfMissing([], demandElaboration);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandElaboration);
        });

        it('should not add a DemandElaboration to an array that contains it', () => {
          const demandElaboration: IDemandElaboration = { id: 123 };
          const demandElaborationCollection: IDemandElaboration[] = [
            {
              ...demandElaboration,
            },
            { id: 456 },
          ];
          expectedResult = service.addDemandElaborationToCollectionIfMissing(demandElaborationCollection, demandElaboration);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a DemandElaboration to an array that doesn't contain it", () => {
          const demandElaboration: IDemandElaboration = { id: 123 };
          const demandElaborationCollection: IDemandElaboration[] = [{ id: 456 }];
          expectedResult = service.addDemandElaborationToCollectionIfMissing(demandElaborationCollection, demandElaboration);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandElaboration);
        });

        it('should add only unique DemandElaboration to an array', () => {
          const demandElaborationArray: IDemandElaboration[] = [{ id: 123 }, { id: 456 }, { id: 35270 }];
          const demandElaborationCollection: IDemandElaboration[] = [{ id: 123 }];
          expectedResult = service.addDemandElaborationToCollectionIfMissing(demandElaborationCollection, ...demandElaborationArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const demandElaboration: IDemandElaboration = { id: 123 };
          const demandElaboration2: IDemandElaboration = { id: 456 };
          expectedResult = service.addDemandElaborationToCollectionIfMissing([], demandElaboration, demandElaboration2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(demandElaboration);
          expect(expectedResult).toContain(demandElaboration2);
        });

        it('should accept null and undefined values', () => {
          const demandElaboration: IDemandElaboration = { id: 123 };
          expectedResult = service.addDemandElaborationToCollectionIfMissing([], null, demandElaboration, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(demandElaboration);
        });

        it('should return initial array if no DemandElaboration is added', () => {
          const demandElaborationCollection: IDemandElaboration[] = [{ id: 123 }];
          expectedResult = service.addDemandElaborationToCollectionIfMissing(demandElaborationCollection, undefined, null);
          expect(expectedResult).toEqual(demandElaborationCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
