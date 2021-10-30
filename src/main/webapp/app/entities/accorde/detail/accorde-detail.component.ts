import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccorde } from '../accorde.model';

@Component({
  selector: 'jhi-accorde-detail',
  templateUrl: './accorde-detail.component.html',
})
export class AccordeDetailComponent implements OnInit {
  accorde: IAccorde | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accorde }) => {
      this.accorde = accorde;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
