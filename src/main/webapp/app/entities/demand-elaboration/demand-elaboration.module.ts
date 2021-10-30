import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemandElaborationComponent } from './list/demand-elaboration.component';
import { DemandElaborationDetailComponent } from './detail/demand-elaboration-detail.component';
import { DemandElaborationUpdateComponent } from './update/demand-elaboration-update.component';
import { DemandElaborationDeleteDialogComponent } from './delete/demand-elaboration-delete-dialog.component';
import { DemandElaborationRoutingModule } from './route/demand-elaboration-routing.module';

@NgModule({
  imports: [SharedModule, DemandElaborationRoutingModule],
  declarations: [
    DemandElaborationComponent,
    DemandElaborationDetailComponent,
    DemandElaborationUpdateComponent,
    DemandElaborationDeleteDialogComponent,
  ],
  entryComponents: [DemandElaborationDeleteDialogComponent],
})
export class DemandElaborationModule {}
