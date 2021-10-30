import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandElaborationComponent } from '../list/demand-elaboration.component';
import { DemandElaborationDetailComponent } from '../detail/demand-elaboration-detail.component';
import { DemandElaborationUpdateComponent } from '../update/demand-elaboration-update.component';
import { DemandElaborationRoutingResolveService } from './demand-elaboration-routing-resolve.service';

const demandElaborationRoute: Routes = [
  {
    path: '',
    component: DemandElaborationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandElaborationDetailComponent,
    resolve: {
      demandElaboration: DemandElaborationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandElaborationUpdateComponent,
    resolve: {
      demandElaboration: DemandElaborationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandElaborationUpdateComponent,
    resolve: {
      demandElaboration: DemandElaborationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandElaborationRoute)],
  exports: [RouterModule],
})
export class DemandElaborationRoutingModule {}
