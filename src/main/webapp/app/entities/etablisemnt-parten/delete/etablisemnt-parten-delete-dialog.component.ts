import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtablisemntParten } from '../etablisemnt-parten.model';
import { EtablisemntPartenService } from '../service/etablisemnt-parten.service';

@Component({
  templateUrl: './etablisemnt-parten-delete-dialog.component.html',
})
export class EtablisemntPartenDeleteDialogComponent {
  etablisemntParten?: IEtablisemntParten;

  constructor(protected etablisemntPartenService: EtablisemntPartenService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etablisemntPartenService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
