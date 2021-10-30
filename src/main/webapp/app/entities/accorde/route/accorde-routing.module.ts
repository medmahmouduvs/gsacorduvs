import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccordeComponent } from '../list/accorde.component';
import { AccordeDetailComponent } from '../detail/accorde-detail.component';
import { AccordeUpdateComponent } from '../update/accorde-update.component';
import { AccordeRoutingResolveService } from './accorde-routing-resolve.service';

const accordeRoute: Routes = [
  {
    path: '',
    component: AccordeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccordeDetailComponent,
    resolve: {
      accorde: AccordeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccordeUpdateComponent,
    resolve: {
      accorde: AccordeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccordeUpdateComponent,
    resolve: {
      accorde: AccordeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accordeRoute)],
  exports: [RouterModule],
})
export class AccordeRoutingModule {}
