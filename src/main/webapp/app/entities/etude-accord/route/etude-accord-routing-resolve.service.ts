import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEtudeAccord, EtudeAccord } from '../etude-accord.model';
import { EtudeAccordService } from '../service/etude-accord.service';

@Injectable({ providedIn: 'root' })
export class EtudeAccordRoutingResolveService implements Resolve<IEtudeAccord> {
  constructor(protected service: EtudeAccordService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtudeAccord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((etudeAccord: HttpResponse<EtudeAccord>) => {
          if (etudeAccord.body) {
            return of(etudeAccord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtudeAccord());
  }
}
