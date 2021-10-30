jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEtudeAccord, EtudeAccord } from '../etude-accord.model';
import { EtudeAccordService } from '../service/etude-accord.service';

import { EtudeAccordRoutingResolveService } from './etude-accord-routing-resolve.service';

describe('Service Tests', () => {
  describe('EtudeAccord routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EtudeAccordRoutingResolveService;
    let service: EtudeAccordService;
    let resultEtudeAccord: IEtudeAccord | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EtudeAccordRoutingResolveService);
      service = TestBed.inject(EtudeAccordService);
      resultEtudeAccord = undefined;
    });

    describe('resolve', () => {
      it('should return IEtudeAccord returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEtudeAccord = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEtudeAccord).toEqual({ id: 123 });
      });

      it('should return new IEtudeAccord if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEtudeAccord = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEtudeAccord).toEqual(new EtudeAccord());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EtudeAccord })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEtudeAccord = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEtudeAccord).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
