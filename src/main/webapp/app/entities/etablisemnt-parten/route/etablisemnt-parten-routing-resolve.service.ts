import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEtablisemntParten, EtablisemntParten } from '../etablisemnt-parten.model';
import { EtablisemntPartenService } from '../service/etablisemnt-parten.service';

@Injectable({ providedIn: 'root' })
export class EtablisemntPartenRoutingResolveService implements Resolve<IEtablisemntParten> {
  constructor(protected service: EtablisemntPartenService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtablisemntParten> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((etablisemntParten: HttpResponse<EtablisemntParten>) => {
          if (etablisemntParten.body) {
            return of(etablisemntParten.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtablisemntParten());
  }
}
