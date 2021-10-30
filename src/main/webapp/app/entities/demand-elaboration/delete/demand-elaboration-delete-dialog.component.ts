import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandElaboration } from '../demand-elaboration.model';
import { DemandElaborationService } from '../service/demand-elaboration.service';

@Component({
  templateUrl: './demand-elaboration-delete-dialog.component.html',
})
export class DemandElaborationDeleteDialogComponent {
  demandElaboration?: IDemandElaboration;

  constructor(protected demandElaborationService: DemandElaborationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandElaborationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
