import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EtablisUserComponent } from './list/etablis-user.component';
import { EtablisUserDetailComponent } from './detail/etablis-user-detail.component';
import { EtablisUserUpdateComponent } from './update/etablis-user-update.component';
import { EtablisUserDeleteDialogComponent } from './delete/etablis-user-delete-dialog.component';
import { EtablisUserRoutingModule } from './route/etablis-user-routing.module';

@NgModule({
  imports: [SharedModule, EtablisUserRoutingModule],
  declarations: [EtablisUserComponent, EtablisUserDetailComponent, EtablisUserUpdateComponent, EtablisUserDeleteDialogComponent],
  entryComponents: [EtablisUserDeleteDialogComponent],
})
export class EtablisUserModule {}
