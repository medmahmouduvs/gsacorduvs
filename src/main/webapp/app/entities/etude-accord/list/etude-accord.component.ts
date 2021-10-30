import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtudeAccord } from '../etude-accord.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { EtudeAccordService } from '../service/etude-accord.service';
import { EtudeAccordDeleteDialogComponent } from '../delete/etude-accord-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-etude-accord',
  templateUrl: './etude-accord.component.html',
})
export class EtudeAccordComponent implements OnInit {
  etudeAccords: IEtudeAccord[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(protected etudeAccordService: EtudeAccordService, protected modalService: NgbModal, protected parseLinks: ParseLinks) {
    this.etudeAccords = [];
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

    this.etudeAccordService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IEtudeAccord[]>) => {
          this.isLoading = false;
          this.paginateEtudeAccords(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.etudeAccords = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEtudeAccord): number {
    return item.id!;
  }

  delete(etudeAccord: IEtudeAccord): void {
    const modalRef = this.modalService.open(EtudeAccordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.etudeAccord = etudeAccord;
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

  protected paginateEtudeAccords(data: IEtudeAccord[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.etudeAccords.push(d);
      }
    }
  }
}
