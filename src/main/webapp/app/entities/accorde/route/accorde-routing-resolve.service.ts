import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccorde, Accorde } from '../accorde.model';
import { AccordeService } from '../service/accorde.service';

@Injectable({ providedIn: 'root' })
export class AccordeRoutingResolveService implements Resolve<IAccorde> {
  constructor(protected service: AccordeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccorde> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accorde: HttpResponse<Accorde>) => {
          if (accorde.body) {
            return of(accorde.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Accorde());
  }
}
