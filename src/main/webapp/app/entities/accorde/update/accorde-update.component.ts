import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAccorde, Accorde } from '../accorde.model';
import { AccordeService } from '../service/accorde.service';
import { IEspaceAcEtEl } from 'app/entities/espace-ac-et-el/espace-ac-et-el.model';
import { EspaceAcEtElService } from 'app/entities/espace-ac-et-el/service/espace-ac-et-el.service';
import { IEtablisemntParten } from 'app/entities/etablisemnt-parten/etablisemnt-parten.model';
import { EtablisemntPartenService } from 'app/entities/etablisemnt-parten/service/etablisemnt-parten.service';

@Component({
  selector: 'jhi-accorde-update',
  templateUrl: './accorde-update.component.html',
})
export class AccordeUpdateComponent implements OnInit {
  isSaving = false;

  espaceAcEtElsSharedCollection: IEspaceAcEtEl[] = [];
  etablisemntPartensSharedCollection: IEtablisemntParten[] = [];

  editForm = this.fb.group({
    id: [],
    titre: [],
    teritornature: [],
    statusacord: [],
    dateAccord: [],
    signaturereacteru: [],
    signatureDiircore: [],
    signatureChefEtab: [],
    article: [],
    espaceAcEtEl: [],
    etablisemntPartens: [],
  });

  constructor(
    protected accordeService: AccordeService,
    protected espaceAcEtElService: EspaceAcEtElService,
    protected etablisemntPartenService: EtablisemntPartenService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accorde }) => {
      this.updateForm(accorde);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accorde = this.createFromForm();
    if (accorde.id !== undefined) {
      this.subscribeToSaveResponse(this.accordeService.update(accorde));
    } else {
      this.subscribeToSaveResponse(this.accordeService.create(accorde));
    }
  }

  trackEspaceAcEtElById(index: number, item: IEspaceAcEtEl): number {
    return item.id!;
  }

  trackEtablisemntPartenById(index: number, item: IEtablisemntParten): number {
    return item.id!;
  }

  getSelectedEtablisemntParten(option: IEtablisemntParten, selectedVals?: IEtablisemntParten[]): IEtablisemntParten {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccorde>>): void {
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

  protected updateForm(accorde: IAccorde): void {
    this.editForm.patchValue({
      id: accorde.id,
      titre: accorde.titre,
      teritornature: accorde.teritornature,
      statusacord: accorde.statusacord,
      dateAccord: accorde.dateAccord,
      signaturereacteru: accorde.signaturereacteru,
      signatureDiircore: accorde.signatureDiircore,
      signatureChefEtab: accorde.signatureChefEtab,
      article: accorde.article,
      espaceAcEtEl: accorde.espaceAcEtEl,
      etablisemntPartens: accorde.etablisemntPartens,
    });

    this.espaceAcEtElsSharedCollection = this.espaceAcEtElService.addEspaceAcEtElToCollectionIfMissing(
      this.espaceAcEtElsSharedCollection,
      accorde.espaceAcEtEl
    );
    this.etablisemntPartensSharedCollection = this.etablisemntPartenService.addEtablisemntPartenToCollectionIfMissing(
      this.etablisemntPartensSharedCollection,
      ...(accorde.etablisemntPartens ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.espaceAcEtElService
      .query()
      .pipe(map((res: HttpResponse<IEspaceAcEtEl[]>) => res.body ?? []))
      .pipe(
        map((espaceAcEtEls: IEspaceAcEtEl[]) =>
          this.espaceAcEtElService.addEspaceAcEtElToCollectionIfMissing(espaceAcEtEls, this.editForm.get('espaceAcEtEl')!.value)
        )
      )
      .subscribe((espaceAcEtEls: IEspaceAcEtEl[]) => (this.espaceAcEtElsSharedCollection = espaceAcEtEls));

    this.etablisemntPartenService
      .query()
      .pipe(map((res: HttpResponse<IEtablisemntParten[]>) => res.body ?? []))
      .pipe(
        map((etablisemntPartens: IEtablisemntParten[]) =>
          this.etablisemntPartenService.addEtablisemntPartenToCollectionIfMissing(
            etablisemntPartens,
            ...(this.editForm.get('etablisemntPartens')!.value ?? [])
          )
        )
      )
      .subscribe((etablisemntPartens: IEtablisemntParten[]) => (this.etablisemntPartensSharedCollection = etablisemntPartens));
  }

  protected createFromForm(): IAccorde {
    return {
      ...new Accorde(),
      id: this.editForm.get(['id'])!.value,
      titre: this.editForm.get(['titre'])!.value,
      teritornature: this.editForm.get(['teritornature'])!.value,
      statusacord: this.editForm.get(['statusacord'])!.value,
      dateAccord: this.editForm.get(['dateAccord'])!.value,
      signaturereacteru: this.editForm.get(['signaturereacteru'])!.value,
      signatureDiircore: this.editForm.get(['signatureDiircore'])!.value,
      signatureChefEtab: this.editForm.get(['signatureChefEtab'])!.value,
      article: this.editForm.get(['article'])!.value,
      espaceAcEtEl: this.editForm.get(['espaceAcEtEl'])!.value,
      etablisemntPartens: this.editForm.get(['etablisemntPartens'])!.value,
    };
  }
}
