import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtablisUser } from '../etablis-user.model';

@Component({
  selector: 'jhi-etablis-user-detail',
  templateUrl: './etablis-user-detail.component.html',
})
export class EtablisUserDetailComponent implements OnInit {
  etablisUser: IEtablisUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablisUser }) => {
      this.etablisUser = etablisUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
