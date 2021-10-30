import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEspaceAcEtEl, EspaceAcEtEl } from '../espace-ac-et-el.model';
import { EspaceAcEtElService } from '../service/espace-ac-et-el.service';

@Injectable({ providedIn: 'root' })
export class EspaceAcEtElRoutingResolveService implements Resolve<IEspaceAcEtEl> {
  constructor(protected service: EspaceAcEtElService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEspaceAcEtEl> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((espaceAcEtEl: HttpResponse<EspaceAcEtEl>) => {
          if (espaceAcEtEl.body) {
            return of(espaceAcEtEl.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EspaceAcEtEl());
  }
}
