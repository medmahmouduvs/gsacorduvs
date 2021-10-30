import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEtablisemntParten, EtablisemntParten } from '../etablisemnt-parten.model';

import { EtablisemntPartenService } from './etablisemnt-parten.service';

describe('Service Tests', () => {
  describe('EtablisemntParten Service', () => {
    let service: EtablisemntPartenService;
    let httpMock: HttpTestingController;
    let elemDefault: IEtablisemntParten;
    let expectedResult: IEtablisemntParten | IEtablisemntParten[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EtablisemntPartenService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        contry: 'AAAAAAA',
        nameEtab: 'AAAAAAA',
        domain: 'AAAAAAA',
        mention: 'AAAAAAA',
        representantname: 'AAAAAAA',
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

      it('should create a EtablisemntParten', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EtablisemntParten()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EtablisemntParten', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            contry: 'BBBBBB',
            nameEtab: 'BBBBBB',
            domain: 'BBBBBB',
            mention: 'BBBBBB',
            representantname: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EtablisemntParten', () => {
        const patchObject = Object.assign(
          {
            contry: 'BBBBBB',
            mention: 'BBBBBB',
            representantname: 'BBBBBB',
          },
          new EtablisemntParten()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EtablisemntParten', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            contry: 'BBBBBB',
            nameEtab: 'BBBBBB',
            domain: 'BBBBBB',
            mention: 'BBBBBB',
            representantname: 'BBBBBB',
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

      it('should delete a EtablisemntParten', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEtablisemntPartenToCollectionIfMissing', () => {
        it('should add a EtablisemntParten to an empty array', () => {
          const etablisemntParten: IEtablisemntParten = { id: 123 };
          expectedResult = service.addEtablisemntPartenToCollectionIfMissing([], etablisemntParten);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(etablisemntParten);
        });

        it('should not add a EtablisemntParten to an array that contains it', () => {
          const etablisemntParten: IEtablisemntParten = { id: 123 };
          const etablisemntPartenCollection: IEtablisemntParten[] = [
            {
              ...etablisemntParten,
            },
            { id: 456 },
          ];
          expectedResult = service.addEtablisemntPartenToCollectionIfMissing(etablisemntPartenCollection, etablisemntParten);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EtablisemntParten to an array that doesn't contain it", () => {
          const etablisemntParten: IEtablisemntParten = { id: 123 };
          const etablisemntPartenCollection: IEtablisemntParten[] = [{ id: 456 }];
          expectedResult = service.addEtablisemntPartenToCollectionIfMissing(etablisemntPartenCollection, etablisemntParten);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(etablisemntParten);
        });

        it('should add only unique EtablisemntParten to an array', () => {
          const etablisemntPartenArray: IEtablisemntParten[] = [{ id: 123 }, { id: 456 }, { id: 68673 }];
          const etablisemntPartenCollection: IEtablisemntParten[] = [{ id: 123 }];
          expectedResult = service.addEtablisemntPartenToCollectionIfMissing(etablisemntPartenCollection, ...etablisemntPartenArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const etablisemntParten: IEtablisemntParten = { id: 123 };
          const etablisemntParten2: IEtablisemntParten = { id: 456 };
          expectedResult = service.addEtablisemntPartenToCollectionIfMissing([], etablisemntParten, etablisemntParten2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(etablisemntParten);
          expect(expectedResult).toContain(etablisemntParten2);
        });

        it('should accept null and undefined values', () => {
          const etablisemntParten: IEtablisemntParten = { id: 123 };
          expectedResult = service.addEtablisemntPartenToCollectionIfMissing([], null, etablisemntParten, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(etablisemntParten);
        });

        it('should return initial array if no EtablisemntParten is added', () => {
          const etablisemntPartenCollection: IEtablisemntParten[] = [{ id: 123 }];
          expectedResult = service.addEtablisemntPartenToCollectionIfMissing(etablisemntPartenCollection, undefined, null);
          expect(expectedResult).toEqual(etablisemntPartenCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
