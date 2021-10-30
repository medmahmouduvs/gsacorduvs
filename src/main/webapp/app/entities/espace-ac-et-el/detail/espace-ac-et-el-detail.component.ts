import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEspaceAcEtEl } from '../espace-ac-et-el.model';

@Component({
  selector: 'jhi-espace-ac-et-el-detail',
  templateUrl: './espace-ac-et-el-detail.component.html',
})
export class EspaceAcEtElDetailComponent implements OnInit {
  espaceAcEtEl: IEspaceAcEtEl | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ espaceAcEtEl }) => {
      this.espaceAcEtEl = espaceAcEtEl;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
