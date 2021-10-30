jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEtablisUser, EtablisUser } from '../etablis-user.model';
import { EtablisUserService } from '../service/etablis-user.service';

import { EtablisUserRoutingResolveService } from './etablis-user-routing-resolve.service';

describe('Service Tests', () => {
  describe('EtablisUser routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EtablisUserRoutingResolveService;
    let service: EtablisUserService;
    let resultEtablisUser: IEtablisUser | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EtablisUserRoutingResolveService);
      service = TestBed.inject(EtablisUserService);
      resultEtablisUser = undefined;
    });

    describe('resolve', () => {
      it('should return IEtablisUser returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEtablisUser = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEtablisUser).toEqual({ id: 123 });
      });

      it('should return new IEtablisUser if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEtablisUser = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEtablisUser).toEqual(new EtablisUser());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EtablisUser })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEtablisUser = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEtablisUser).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
