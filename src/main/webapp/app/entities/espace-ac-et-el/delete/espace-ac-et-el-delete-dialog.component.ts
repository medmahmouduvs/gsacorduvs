import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEspaceAcEtEl } from '../espace-ac-et-el.model';
import { EspaceAcEtElService } from '../service/espace-ac-et-el.service';

@Component({
  templateUrl: './espace-ac-et-el-delete-dialog.component.html',
})
export class EspaceAcEtElDeleteDialogComponent {
  espaceAcEtEl?: IEspaceAcEtEl;

  constructor(protected espaceAcEtElService: EspaceAcEtElService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.espaceAcEtElService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
