import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EspaceAcEtElComponent } from '../list/espace-ac-et-el.component';
import { EspaceAcEtElDetailComponent } from '../detail/espace-ac-et-el-detail.component';
import { EspaceAcEtElUpdateComponent } from '../update/espace-ac-et-el-update.component';
import { EspaceAcEtElRoutingResolveService } from './espace-ac-et-el-routing-resolve.service';

const espaceAcEtElRoute: Routes = [
  {
    path: '',
    component: EspaceAcEtElComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EspaceAcEtElDetailComponent,
    resolve: {
      espaceAcEtEl: EspaceAcEtElRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EspaceAcEtElUpdateComponent,
    resolve: {
      espaceAcEtEl: EspaceAcEtElRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EspaceAcEtElUpdateComponent,
    resolve: {
      espaceAcEtEl: EspaceAcEtElRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(espaceAcEtElRoute)],
  exports: [RouterModule],
})
export class EspaceAcEtElRoutingModule {}
