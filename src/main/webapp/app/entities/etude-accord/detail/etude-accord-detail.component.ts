import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtudeAccord } from '../etude-accord.model';

@Component({
  selector: 'jhi-etude-accord-detail',
  templateUrl: './etude-accord-detail.component.html',
})
export class EtudeAccordDetailComponent implements OnInit {
  etudeAccord: IEtudeAccord | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etudeAccord }) => {
      this.etudeAccord = etudeAccord;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
