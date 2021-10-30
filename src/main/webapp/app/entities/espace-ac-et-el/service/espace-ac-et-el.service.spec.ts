import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEspaceAcEtEl, EspaceAcEtEl } from '../espace-ac-et-el.model';

import { EspaceAcEtElService } from './espace-ac-et-el.service';

describe('Service Tests', () => {
  describe('EspaceAcEtEl Service', () => {
    let service: EspaceAcEtElService;
    let httpMock: HttpTestingController;
    let elemDefault: IEspaceAcEtEl;
    let expectedResult: IEspaceAcEtEl | IEspaceAcEtEl[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EspaceAcEtElService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        handle: 'AAAAAAA',
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

      it('should create a EspaceAcEtEl', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EspaceAcEtEl()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EspaceAcEtEl', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            handle: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EspaceAcEtEl', () => {
        const patchObject = Object.assign(
          {
            handle: 'BBBBBB',
          },
          new EspaceAcEtEl()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EspaceAcEtEl', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            handle: 'BBBBBB',
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

      it('should delete a EspaceAcEtEl', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEspaceAcEtElToCollectionIfMissing', () => {
        it('should add a EspaceAcEtEl to an empty array', () => {
          const espaceAcEtEl: IEspaceAcEtEl = { id: 123 };
          expectedResult = service.addEspaceAcEtElToCollectionIfMissing([], espaceAcEtEl);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(espaceAcEtEl);
        });

        it('should not add a EspaceAcEtEl to an array that contains it', () => {
          const espaceAcEtEl: IEspaceAcEtEl = { id: 123 };
          const espaceAcEtElCollection: IEspaceAcEtEl[] = [
            {
              ...espaceAcEtEl,
            },
            { id: 456 },
          ];
          expectedResult = service.addEspaceAcEtElToCollectionIfMissing(espaceAcEtElCollection, espaceAcEtEl);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EspaceAcEtEl to an array that doesn't contain it", () => {
          const espaceAcEtEl: IEspaceAcEtEl = { id: 123 };
          const espaceAcEtElCollection: IEspaceAcEtEl[] = [{ id: 456 }];
          expectedResult = service.addEspaceAcEtElToCollectionIfMissing(espaceAcEtElCollection, espaceAcEtEl);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(espaceAcEtEl);
        });

        it('should add only unique EspaceAcEtEl to an array', () => {
          const espaceAcEtElArray: IEspaceAcEtEl[] = [{ id: 123 }, { id: 456 }, { id: 10935 }];
          const espaceAcEtElCollection: IEspaceAcEtEl[] = [{ id: 123 }];
          expectedResult = service.addEspaceAcEtElToCollectionIfMissing(espaceAcEtElCollection, ...espaceAcEtElArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const espaceAcEtEl: IEspaceAcEtEl = { id: 123 };
          const espaceAcEtEl2: IEspaceAcEtEl = { id: 456 };
          expectedResult = service.addEspaceAcEtElToCollectionIfMissing([], espaceAcEtEl, espaceAcEtEl2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(espaceAcEtEl);
          expect(expectedResult).toContain(espaceAcEtEl2);
        });

        it('should accept null and undefined values', () => {
          const espaceAcEtEl: IEspaceAcEtEl = { id: 123 };
          expectedResult = service.addEspaceAcEtElToCollectionIfMissing([], null, espaceAcEtEl, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(espaceAcEtEl);
        });

        it('should return initial array if no EspaceAcEtEl is added', () => {
          const espaceAcEtElCollection: IEspaceAcEtEl[] = [{ id: 123 }];
          expectedResult = service.addEspaceAcEtElToCollectionIfMissing(espaceAcEtElCollection, undefined, null);
          expect(expectedResult).toEqual(espaceAcEtElCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
