import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEtudeAccord, EtudeAccord } from '../etude-accord.model';
import { EtudeAccordService } from '../service/etude-accord.service';
import { IEspaceAcEtEl } from 'app/entities/espace-ac-et-el/espace-ac-et-el.model';
import { EspaceAcEtElService } from 'app/entities/espace-ac-et-el/service/espace-ac-et-el.service';
import { IAccorde } from 'app/entities/accorde/accorde.model';
import { AccordeService } from 'app/entities/accorde/service/accorde.service';

@Component({
  selector: 'jhi-etude-accord-update',
  templateUrl: './etude-accord-update.component.html',
})
export class EtudeAccordUpdateComponent implements OnInit {
  isSaving = false;

  espaceAcEtElsSharedCollection: IEspaceAcEtEl[] = [];
  accordesSharedCollection: IAccorde[] = [];

  editForm = this.fb.group({
    id: [],
    titre: [],
    dateEtude: [],
    motiveDirCoor: [],
    signaturereacteru: [],
    signatureDiircore: [],
    signatureChefEtab: [],
    espaceAcEtEl: [],
    accordes: [],
  });

  constructor(
    protected etudeAccordService: EtudeAccordService,
    protected espaceAcEtElService: EspaceAcEtElService,
    protected accordeService: AccordeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etudeAccord }) => {
      this.updateForm(etudeAccord);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etudeAccord = this.createFromForm();
    if (etudeAccord.id !== undefined) {
      this.subscribeToSaveResponse(this.etudeAccordService.update(etudeAccord));
    } else {
      this.subscribeToSaveResponse(this.etudeAccordService.create(etudeAccord));
    }
  }

  trackEspaceAcEtElById(index: number, item: IEspaceAcEtEl): number {
    return item.id!;
  }

  trackAccordeById(index: number, item: IAccorde): number {
    return item.id!;
  }

  getSelectedAccorde(option: IAccorde, selectedVals?: IAccorde[]): IAccorde {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtudeAccord>>): void {
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

  protected updateForm(etudeAccord: IEtudeAccord): void {
    this.editForm.patchValue({
      id: etudeAccord.id,
      titre: etudeAccord.titre,
      dateEtude: etudeAccord.dateEtude,
      motiveDirCoor: etudeAccord.motiveDirCoor,
      signaturereacteru: etudeAccord.signaturereacteru,
      signatureDiircore: etudeAccord.signatureDiircore,
      signatureChefEtab: etudeAccord.signatureChefEtab,
      espaceAcEtEl: etudeAccord.espaceAcEtEl,
      accordes: etudeAccord.accordes,
    });

    this.espaceAcEtElsSharedCollection = this.espaceAcEtElService.addEspaceAcEtElToCollectionIfMissing(
      this.espaceAcEtElsSharedCollection,
      etudeAccord.espaceAcEtEl
    );
    this.accordesSharedCollection = this.accordeService.addAccordeToCollectionIfMissing(
      this.accordesSharedCollection,
      ...(etudeAccord.accordes ?? [])
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

    this.accordeService
      .query()
      .pipe(map((res: HttpResponse<IAccorde[]>) => res.body ?? []))
      .pipe(
        map((accordes: IAccorde[]) =>
          this.accordeService.addAccordeToCollectionIfMissing(accordes, ...(this.editForm.get('accordes')!.value ?? []))
        )
      )
      .subscribe((accordes: IAccorde[]) => (this.accordesSharedCollection = accordes));
  }

  protected createFromForm(): IEtudeAccord {
    return {
      ...new EtudeAccord(),
      id: this.editForm.get(['id'])!.value,
      titre: this.editForm.get(['titre'])!.value,
      dateEtude: this.editForm.get(['dateEtude'])!.value,
      motiveDirCoor: this.editForm.get(['motiveDirCoor'])!.value,
      signaturereacteru: this.editForm.get(['signaturereacteru'])!.value,
      signatureDiircore: this.editForm.get(['signatureDiircore'])!.value,
      signatureChefEtab: this.editForm.get(['signatureChefEtab'])!.value,
      espaceAcEtEl: this.editForm.get(['espaceAcEtEl'])!.value,
      accordes: this.editForm.get(['accordes'])!.value,
    };
  }
}
