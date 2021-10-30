import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEtablisUser, EtablisUser } from '../etablis-user.model';
import { EtablisUserService } from '../service/etablis-user.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-etablis-user-update',
  templateUrl: './etablis-user-update.component.html',
})
export class EtablisUserUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [],
    email: [],
    position: [],
    username: [],
    password: [],
    internalUser: [],
  });

  constructor(
    protected etablisUserService: EtablisUserService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablisUser }) => {
      this.updateForm(etablisUser);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etablisUser = this.createFromForm();
    if (etablisUser.id !== undefined) {
      this.subscribeToSaveResponse(this.etablisUserService.update(etablisUser));
    } else {
      this.subscribeToSaveResponse(this.etablisUserService.create(etablisUser));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtablisUser>>): void {
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

  protected updateForm(etablisUser: IEtablisUser): void {
    this.editForm.patchValue({
      id: etablisUser.id,
      nome: etablisUser.nome,
      email: etablisUser.email,
      position: etablisUser.position,
      username: etablisUser.username,
      password: etablisUser.password,
      internalUser: etablisUser.internalUser,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, etablisUser.internalUser);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('internalUser')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }

  protected createFromForm(): IEtablisUser {
    return {
      ...new EtablisUser(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      email: this.editForm.get(['email'])!.value,
      position: this.editForm.get(['position'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      internalUser: this.editForm.get(['internalUser'])!.value,
    };
  }
}
