import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccorde } from '../accorde.model';
import { AccordeService } from '../service/accorde.service';

@Component({
  templateUrl: './accorde-delete-dialog.component.html',
})
export class AccordeDeleteDialogComponent {
  accorde?: IAccorde;

  constructor(protected accordeService: AccordeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accordeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
