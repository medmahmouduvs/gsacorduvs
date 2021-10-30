import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEspaceAcEtEl } from '../espace-ac-et-el.model';
import { EspaceAcEtElService } from '../service/espace-ac-et-el.service';
import { EspaceAcEtElDeleteDialogComponent } from '../delete/espace-ac-et-el-delete-dialog.component';

@Component({
  selector: 'jhi-espace-ac-et-el',
  templateUrl: './espace-ac-et-el.component.html',
})
export class EspaceAcEtElComponent implements OnInit {
  espaceAcEtEls?: IEspaceAcEtEl[];
  isLoading = false;

  constructor(protected espaceAcEtElService: EspaceAcEtElService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.espaceAcEtElService.query().subscribe(
      (res: HttpResponse<IEspaceAcEtEl[]>) => {
        this.isLoading = false;
        this.espaceAcEtEls = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEspaceAcEtEl): number {
    return item.id!;
  }

  delete(espaceAcEtEl: IEspaceAcEtEl): void {
    const modalRef = this.modalService.open(EspaceAcEtElDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.espaceAcEtEl = espaceAcEtEl;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
