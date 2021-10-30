import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtablisUser } from '../etablis-user.model';
import { EtablisUserService } from '../service/etablis-user.service';
import { EtablisUserDeleteDialogComponent } from '../delete/etablis-user-delete-dialog.component';

@Component({
  selector: 'jhi-etablis-user',
  templateUrl: './etablis-user.component.html',
})
export class EtablisUserComponent implements OnInit {
  etablisUsers?: IEtablisUser[];
  isLoading = false;

  constructor(protected etablisUserService: EtablisUserService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.etablisUserService.query().subscribe(
      (res: HttpResponse<IEtablisUser[]>) => {
        this.isLoading = false;
        this.etablisUsers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEtablisUser): number {
    return item.id!;
  }

  delete(etablisUser: IEtablisUser): void {
    const modalRef = this.modalService.open(EtablisUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etablisUser = etablisUser;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
