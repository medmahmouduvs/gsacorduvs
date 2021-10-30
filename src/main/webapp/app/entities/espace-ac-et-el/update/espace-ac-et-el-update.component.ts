import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEspaceAcEtEl, EspaceAcEtEl } from '../espace-ac-et-el.model';
import { EspaceAcEtElService } from '../service/espace-ac-et-el.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-espace-ac-et-el-update',
  templateUrl: './espace-ac-et-el-update.component.html',
})
export class EspaceAcEtElUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    handle: [null, [Validators.required]],
    user: [],
  });

  constructor(
    protected espaceAcEtElService: EspaceAcEtElService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ espaceAcEtEl }) => {
      this.updateForm(espaceAcEtEl);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const espaceAcEtEl = this.createFromForm();
    if (espaceAcEtEl.id !== undefined) {
      this.subscribeToSaveResponse(this.espaceAcEtElService.update(espaceAcEtEl));
    } else {
      this.subscribeToSaveResponse(this.espaceAcEtElService.create(espaceAcEtEl));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEspaceAcEtEl>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(espaceAcEtEl: IEspaceAcEtEl): void {
    this.editForm.patchValue({
      id: espaceAcEtEl.id,
      name: espaceAcEtEl.name,
      handle: espaceAcEtEl.handle,
      user: espaceAcEtEl.user,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, espaceAcEtEl.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IEspaceAcEtEl {
    return {
      ...new EspaceAcEtEl(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      handle: this.editForm.get(['handle'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }
}
