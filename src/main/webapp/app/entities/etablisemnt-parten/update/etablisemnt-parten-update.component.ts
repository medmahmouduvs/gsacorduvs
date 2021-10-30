import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEtablisemntParten, EtablisemntParten } from '../etablisemnt-parten.model';
import { EtablisemntPartenService } from '../service/etablisemnt-parten.service';

@Component({
  selector: 'jhi-etablisemnt-parten-update',
  templateUrl: './etablisemnt-parten-update.component.html',
})
export class EtablisemntPartenUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    contry: [],
    nameEtab: [],
    domain: [],
    mention: [],
    representantname: [],
  });

  constructor(
    protected etablisemntPartenService: EtablisemntPartenService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etablisemntParten }) => {
      this.updateForm(etablisemntParten);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etablisemntParten = this.createFromForm();
    if (etablisemntParten.id !== undefined) {
      this.subscribeToSaveResponse(this.etablisemntPartenService.update(etablisemntParten));
    } else {
      this.subscribeToSaveResponse(this.etablisemntPartenService.create(etablisemntParten));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtablisemntParten>>): void {
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

  protected updateForm(etablisemntParten: IEtablisemntParten): void {
    this.editForm.patchValue({
      id: etablisemntParten.id,
      contry: etablisemntParten.contry,
      nameEtab: etablisemntParten.nameEtab,
      domain: etablisemntParten.domain,
      mention: etablisemntParten.mention,
      representantname: etablisemntParten.representantname,
    });
  }

  protected createFromForm(): IEtablisemntParten {
    return {
      ...new EtablisemntParten(),
      id: this.editForm.get(['id'])!.value,
      contry: this.editForm.get(['contry'])!.value,
      nameEtab: this.editForm.get(['nameEtab'])!.value,
      domain: this.editForm.get(['domain'])!.value,
      mention: this.editForm.get(['mention'])!.value,
      representantname: this.editForm.get(['representantname'])!.value,
    };
  }
}
