import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandElaboration } from '../demand-elaboration.model';

import { ASC, DESC, ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { DemandElaborationService } from '../service/demand-elaboration.service';
import { DemandElaborationDeleteDialogComponent } from '../delete/demand-elaboration-delete-dialog.component';
import { ParseLinks } from 'app/core/util/parse-links.service';

@Component({
  selector: 'jhi-demand-elaboration',
  templateUrl: './demand-elaboration.component.html',
})
export class DemandElaborationComponent implements OnInit {
  demandElaborations: IDemandElaboration[];
  isLoading = false;
  itemsPerPage: number;
  links: { [key: string]: number };
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected demandElaborationService: DemandElaborationService,
    protected modalService: NgbModal,
    protected parseLinks: ParseLinks
  ) {
    this.demandElaborations = [];
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

    this.demandElaborationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IDemandElaboration[]>) => {
          this.isLoading = false;
          this.paginateDemandElaborations(res.body, res.headers);
        },
        () => {
          this.isLoading = false;
        }
      );
  }

  reset(): void {
    this.page = 0;
    this.demandElaborations = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IDemandElaboration): number {
    return item.id!;
  }

  delete(demandElaboration: IDemandElaboration): void {
    const modalRef = this.modalService.open(DemandElaborationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.demandElaboration = demandElaboration;
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

  protected paginateDemandElaborations(data: IDemandElaboration[] | null, headers: HttpHeaders): void {
    this.links = this.parseLinks.parse(headers.get('link') ?? '');
    if (data) {
      for (const d of data) {
        this.demandElaborations.push(d);
      }
    }
  }
}
