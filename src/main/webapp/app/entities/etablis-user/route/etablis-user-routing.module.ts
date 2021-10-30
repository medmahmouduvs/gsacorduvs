import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EtablisUserComponent } from '../list/etablis-user.component';
import { EtablisUserDetailComponent } from '../detail/etablis-user-detail.component';
import { EtablisUserUpdateComponent } from '../update/etablis-user-update.component';
import { EtablisUserRoutingResolveService } from './etablis-user-routing-resolve.service';

const etablisUserRoute: Routes = [
  {
    path: '',
    component: EtablisUserComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtablisUserDetailComponent,
    resolve: {
      etablisUser: EtablisUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtablisUserUpdateComponent,
    resolve: {
      etablisUser: EtablisUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtablisUserUpdateComponent,
    resolve: {
      etablisUser: EtablisUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(etablisUserRoute)],
  exports: [RouterModule],
})
export class EtablisUserRoutingModule {}
