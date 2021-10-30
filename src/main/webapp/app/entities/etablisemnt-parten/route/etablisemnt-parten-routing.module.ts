import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EtablisemntPartenComponent } from '../list/etablisemnt-parten.component';
import { EtablisemntPartenDetailComponent } from '../detail/etablisemnt-parten-detail.component';
import { EtablisemntPartenUpdateComponent } from '../update/etablisemnt-parten-update.component';
import { EtablisemntPartenRoutingResolveService } from './etablisemnt-parten-routing-resolve.service';

const etablisemntPartenRoute: Routes = [
  {
    path: '',
    component: EtablisemntPartenComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtablisemntPartenDetailComponent,
    resolve: {
      etablisemntParten: EtablisemntPartenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtablisemntPartenUpdateComponent,
    resolve: {
      etablisemntParten: EtablisemntPartenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtablisemntPartenUpdateComponent,
    resolve: {
      etablisemntParten: EtablisemntPartenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(etablisemntPartenRoute)],
  exports: [RouterModule],
})
export class EtablisemntPartenRoutingModule {}
