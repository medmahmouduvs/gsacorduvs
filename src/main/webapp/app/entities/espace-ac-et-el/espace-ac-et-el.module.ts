import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EspaceAcEtElComponent } from './list/espace-ac-et-el.component';
import { EspaceAcEtElDetailComponent } from './detail/espace-ac-et-el-detail.component';
import { EspaceAcEtElUpdateComponent } from './update/espace-ac-et-el-update.component';
import { EspaceAcEtElDeleteDialogComponent } from './delete/espace-ac-et-el-delete-dialog.component';
import { EspaceAcEtElRoutingModule } from './route/espace-ac-et-el-routing.module';

@NgModule({
  imports: [SharedModule, EspaceAcEtElRoutingModule],
  declarations: [EspaceAcEtElComponent, EspaceAcEtElDetailComponent, EspaceAcEtElUpdateComponent, EspaceAcEtElDeleteDialogComponent],
  entryComponents: [EspaceAcEtElDeleteDialogComponent],
})
export class EspaceAcEtElModule {}
