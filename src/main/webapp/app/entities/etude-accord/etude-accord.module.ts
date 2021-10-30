import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EtudeAccordComponent } from './list/etude-accord.component';
import { EtudeAccordDetailComponent } from './detail/etude-accord-detail.component';
import { EtudeAccordUpdateComponent } from './update/etude-accord-update.component';
import { EtudeAccordDeleteDialogComponent } from './delete/etude-accord-delete-dialog.component';
import { EtudeAccordRoutingModule } from './route/etude-accord-routing.module';

@NgModule({
  imports: [SharedModule, EtudeAccordRoutingModule],
  declarations: [EtudeAccordComponent, EtudeAccordDetailComponent, EtudeAccordUpdateComponent, EtudeAccordDeleteDialogComponent],
  entryComponents: [EtudeAccordDeleteDialogComponent],
})
export class EtudeAccordModule {}
