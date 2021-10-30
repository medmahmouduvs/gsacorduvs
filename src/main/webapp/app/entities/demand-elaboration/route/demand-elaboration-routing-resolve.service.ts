import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemandElaboration, DemandElaboration } from '../demand-elaboration.model';
import { DemandElaborationService } from '../service/demand-elaboration.service';

@Injectable({ providedIn: 'root' })
export class DemandElaborationRoutingResolveService implements Resolve<IDemandElaboration> {
  constructor(protected service: DemandElaborationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandElaboration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((demandElaboration: HttpResponse<DemandElaboration>) => {
          if (demandElaboration.body) {
            return of(demandElaboration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DemandElaboration());
  }
}
