import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtablisUser } from '../etablis-user.model';
import { EtablisUserService } from '../service/etablis-user.service';

@Component({
  templateUrl: './etablis-user-delete-dialog.component.html',
})
export class EtablisUserDeleteDialogComponent {
  etablisUser?: IEtablisUser;

  constructor(protected etablisUserService: EtablisUserService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etablisUserService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
