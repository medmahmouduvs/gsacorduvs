import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AccordeComponent } from './list/accorde.component';
import { AccordeDetailComponent } from './detail/accorde-detail.component';
import { AccordeUpdateComponent } from './update/accorde-update.component';
import { AccordeDeleteDialogComponent } from './delete/accorde-delete-dialog.component';
import { AccordeRoutingModule } from './route/accorde-routing.module';

@NgModule({
  imports: [SharedModule, AccordeRoutingModule],
  declarations: [AccordeComponent, AccordeDetailComponent, AccordeUpdateComponent, AccordeDeleteDialogComponent],
  entryComponents: [AccordeDeleteDialogComponent],
})
export class AccordeModule {}
