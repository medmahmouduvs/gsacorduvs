import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEtablisUser, EtablisUser } from '../etablis-user.model';
import { EtablisUserService } from '../service/etablis-user.service';

@Injectable({ providedIn: 'root' })
export class EtablisUserRoutingResolveService implements Resolve<IEtablisUser> {
  constructor(protected service: EtablisUserService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtablisUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((etablisUser: HttpResponse<EtablisUser>) => {
          if (etablisUser.body) {
            return of(etablisUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtablisUser());
  }
}
