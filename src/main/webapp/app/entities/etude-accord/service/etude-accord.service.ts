import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEtudeAccord, getEtudeAccordIdentifier } from '../etude-accord.model';

export type EntityResponseType = HttpResponse<IEtudeAccord>;
export type EntityArrayResponseType = HttpResponse<IEtudeAccord[]>;

@Injectable({ providedIn: 'root' })
export class EtudeAccordService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/etude-accords');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(etudeAccord: IEtudeAccord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etudeAccord);
    return this.http
      .post<IEtudeAccord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(etudeAccord: IEtudeAccord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etudeAccord);
    return this.http
      .put<IEtudeAccord>(`${this.resourceUrl}/${getEtudeAccordIdentifier(etudeAccord) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(etudeAccord: IEtudeAccord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(etudeAccord);
    return this.http
      .patch<IEtudeAccord>(`${this.resourceUrl}/${getEtudeAccordIdentifier(etudeAccord) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEtudeAccord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEtudeAccord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEtudeAccordToCollectionIfMissing(
    etudeAccordCollection: IEtudeAccord[],
    ...etudeAccordsToCheck: (IEtudeAccord | null | undefined)[]
  ): IEtudeAccord[] {
    const etudeAccords: IEtudeAccord[] = etudeAccordsToCheck.filter(isPresent);
    if (etudeAccords.length > 0) {
      const etudeAccordCollectionIdentifiers = etudeAccordCollection.map(etudeAccordItem => getEtudeAccordIdentifier(etudeAccordItem)!);
      const etudeAccordsToAdd = etudeAccords.filter(etudeAccordItem => {
        const etudeAccordIdentifier = getEtudeAccordIdentifier(etudeAccordItem);
        if (etudeAccordIdentifier == null || etudeAccordCollectionIdentifiers.includes(etudeAccordIdentifier)) {
          return false;
        }
        etudeAccordCollectionIdentifiers.push(etudeAccordIdentifier);
        return true;
      });
      return [...etudeAccordsToAdd, ...etudeAccordCollection];
    }
    return etudeAccordCollection;
  }

  protected convertDateFromClient(etudeAccord: IEtudeAccord): IEtudeAccord {
    return Object.assign({}, etudeAccord, {
      dateEtude: etudeAccord.dateEtude?.isValid() ? etudeAccord.dateEtude.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateEtude = res.body.dateEtude ? dayjs(res.body.dateEtude) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((etudeAccord: IEtudeAccord) => {
        etudeAccord.dateEtude = etudeAccord.dateEtude ? dayjs(etudeAccord.dateEtude) : undefined;
      });
    }
    return res;
  }
}
