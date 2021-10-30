import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtudeAccord } from '../etude-accord.model';
import { EtudeAccordService } from '../service/etude-accord.service';

@Component({
  templateUrl: './etude-accord-delete-dialog.component.html',
})
export class EtudeAccordDeleteDialogComponent {
  etudeAccord?: IEtudeAccord;

  constructor(protected etudeAccordService: EtudeAccordService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etudeAccordService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
