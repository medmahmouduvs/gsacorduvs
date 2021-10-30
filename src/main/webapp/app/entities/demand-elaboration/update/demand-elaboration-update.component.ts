import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDemandElaboration, DemandElaboration } from '../demand-elaboration.model';
import { DemandElaborationService } from '../service/demand-elaboration.service';
import { IEspaceAcEtEl } from 'app/entities/espace-ac-et-el/espace-ac-et-el.model';
import { EspaceAcEtElService } from 'app/entities/espace-ac-et-el/service/espace-ac-et-el.service';
import { IEtablisemntParten } from 'app/entities/etablisemnt-parten/etablisemnt-parten.model';
import { EtablisemntPartenService } from 'app/entities/etablisemnt-parten/service/etablisemnt-parten.service';

@Component({
  selector: 'jhi-demand-elaboration-update',
  templateUrl: './demand-elaboration-update.component.html',
})
export class DemandElaborationUpdateComponent implements OnInit {
  isSaving = false;

  espaceAcEtElsSharedCollection: IEspaceAcEtEl[] = [];
  etablisemntPartensSharedCollection: IEtablisemntParten[] = [];

  editForm = this.fb.group({
    id: [],
    typeAccord: [],
    titreDemand: [],
    dateDeman: [],
    formeAccord: [],
    signaturedircoor: [],
    espaceAcEtEl: [],
    etablisemntPartens: [],
  });

  constructor(
    protected demandElaborationService: DemandElaborationService,
    protected espaceAcEtElService: EspaceAcEtElService,
    protected etablisemntPartenService: EtablisemntPartenService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demandElaboration }) => {
      this.updateForm(demandElaboration);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demandElaboration = this.createFromForm();
    if (demandElaboration.id !== undefined) {
      this.subscribeToSaveResponse(this.demandElaborationService.update(demandElaboration));
    } else {
      this.subscribeToSaveResponse(this.demandElaborationService.create(demandElaboration));
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemandElaboration>>): void {
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

  protected updateForm(demandElaboration: IDemandElaboration): void {
    this.editForm.patchValue({
      id: demandElaboration.id,
      typeAccord: demandElaboration.typeAccord,
      titreDemand: demandElaboration.titreDemand,
      dateDeman: demandElaboration.dateDeman,
      formeAccord: demandElaboration.formeAccord,
      signaturedircoor: demandElaboration.signaturedircoor,
      espaceAcEtEl: demandElaboration.espaceAcEtEl,
      etablisemntPartens: demandElaboration.etablisemntPartens,
    });

    this.espaceAcEtElsSharedCollection = this.espaceAcEtElService.addEspaceAcEtElToCollectionIfMissing(
      this.espaceAcEtElsSharedCollection,
      demandElaboration.espaceAcEtEl
    );
    this.etablisemntPartensSharedCollection = this.etablisemntPartenService.addEtablisemntPartenToCollectionIfMissing(
      this.etablisemntPartensSharedCollection,
      ...(demandElaboration.etablisemntPartens ?? [])
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

  protected createFromForm(): IDemandElaboration {
    return {
      ...new DemandElaboration(),
      id: this.editForm.get(['id'])!.value,
      typeAccord: this.editForm.get(['typeAccord'])!.value,
      titreDemand: this.editForm.get(['titreDemand'])!.value,
      dateDeman: this.editForm.get(['dateDeman'])!.value,
      formeAccord: this.editForm.get(['formeAccord'])!.value,
      signaturedircoor: this.editForm.get(['signaturedircoor'])!.value,
      espaceAcEtEl: this.editForm.get(['espaceAcEtEl'])!.value,
      etablisemntPartens: this.editForm.get(['etablisemntPartens'])!.value,
    };
  }
}
