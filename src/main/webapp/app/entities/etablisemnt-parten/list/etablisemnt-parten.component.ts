import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtablisemntParten } from '../etablisemnt-parten.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { EtablisemntPartenService } from '../service/etablisemnt-parten.service';
import { EtablisemntPartenDeleteDialogComponent } from '../delete/etablisemnt-parten-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-etablisemnt-parten',
  templateUrl: './etablisemnt-parten.component.html',
})
export class EtablisemntPartenComponent implements OnInit {
  etablisemntPartens: IEtablisemntParten[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected etablisemntPartenService: EtablisemntPartenService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.etablisemntPartens = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.isLoading = true;

    this.etablisemntPartenService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IEtablisemntParten[]>) => {
          this.isLoading = false;
          this.paginateEtablisemntPartens(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.etablisemntPartens = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEtablisemntParten): number {
    return item.id!;
  }

  delete(etablisemntParten: IEtablisemntParten): void {
    const modalRef = this.modalService.open(EtablisemntPartenDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etablisemntParten = etablisemntParten;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.reset();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEtablisemntPartens(data: IEtablisemntParten[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.etablisemntPartens.push(d);
      }
    }
  }
}
