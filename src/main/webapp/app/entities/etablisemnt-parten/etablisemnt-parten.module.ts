import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EtablisemntPartenComponent } from './list/etablisemnt-parten.component';
import { EtablisemntPartenDetailComponent } from './detail/etablisemnt-parten-detail.component';
import { EtablisemntPartenUpdateComponent } from './update/etablisemnt-parten-update.component';
import { EtablisemntPartenDeleteDialogComponent } from './delete/etablisemnt-parten-delete-dialog.component';
import { EtablisemntPartenRoutingModule } from './route/etablisemnt-parten-routing.module';

@NgModule({
  imports: [SharedModule, EtablisemntPartenRoutingModule],
  declarations: [
    EtablisemntPartenComponent,
    EtablisemntPartenDetailComponent,
    EtablisemntPartenUpdateComponent,
    EtablisemntPartenDeleteDialogComponent,
  ],
  entryComponents: [EtablisemntPartenDeleteDialogComponent],
})
export class EtablisemntPartenModule {}
