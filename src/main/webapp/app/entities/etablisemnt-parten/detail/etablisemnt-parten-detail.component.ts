import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtablisemntParten } from '../etablisemnt-parten.model';

@Component({
  selector: 'jhi-etablisemnt-parten-detail',
  templateUrl: './etablisemnt-parten-detail.component.html',
})
export class EtablisemntPartenDetailComponent implements OnInit {
  etablisemntParten: IEtablisemntParten | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablisemntParten }) => {
      this.etablisemntParten = etablisemntParten;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
