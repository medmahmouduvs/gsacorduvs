import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandElaboration } from '../demand-elaboration.model';

@Component({
  selector: 'jhi-demand-elaboration-detail',
  templateUrl: './demand-elaboration-detail.component.html',
})
export class DemandElaborationDetailComponent implements OnInit {
  demandElaboration: IDemandElaboration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandElaboration }) => {
      this.demandElaboration = demandElaboration;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
