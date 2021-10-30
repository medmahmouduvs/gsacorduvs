jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEspaceAcEtEl, EspaceAcEtEl } from '../espace-ac-et-el.model';
import { EspaceAcEtElService } from '../service/espace-ac-et-el.service';

import { EspaceAcEtElRoutingResolveService } from './espace-ac-et-el-routing-resolve.service';

describe('Service Tests', () => {
  describe('EspaceAcEtEl routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EspaceAcEtElRoutingResolveService;
    let service: EspaceAcEtElService;
    let resultEspaceAcEtEl: IEspaceAcEtEl | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EspaceAcEtElRoutingResolveService);
      service = TestBed.inject(EspaceAcEtElService);
      resultEspaceAcEtEl = undefined;
    });

    describe('resolve', () => {
      it('should return IEspaceAcEtEl returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEspaceAcEtEl = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEspaceAcEtEl).toEqual({ id: 123 });
      });

      it('should return new IEspaceAcEtEl if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEspaceAcEtEl = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEspaceAcEtEl).toEqual(new EspaceAcEtEl());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EspaceAcEtEl })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEspaceAcEtEl = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEspaceAcEtEl).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
