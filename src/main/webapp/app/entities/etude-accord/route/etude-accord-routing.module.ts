import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EtudeAccordComponent } from '../list/etude-accord.component';
import { EtudeAccordDetailComponent } from '../detail/etude-accord-detail.component';
import { EtudeAccordUpdateComponent } from '../update/etude-accord-update.component';
import { EtudeAccordRoutingResolveService } from './etude-accord-routing-resolve.service';

const etudeAccordRoute: Routes = [
  {
    path: '',
    component: EtudeAccordComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtudeAccordDetailComponent,
    resolve: {
      etudeAccord: EtudeAccordRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtudeAccordUpdateComponent,
    resolve: {
      etudeAccord: EtudeAccordRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtudeAccordUpdateComponent,
    resolve: {
      etudeAccord: EtudeAccordRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(etudeAccordRoute)],
  exports: [RouterModule],
})
export class EtudeAccordRoutingModule {}
