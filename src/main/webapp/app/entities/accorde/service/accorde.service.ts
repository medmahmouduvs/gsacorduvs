import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccorde, getAccordeIdentifier } from '../accorde.model';

export type EntityResponseType = HttpResponse<IAccorde>;
export type EntityArrayResponseType = HttpResponse<IAccorde[]>;

@Injectable({ providedIn: 'root' })
export class AccordeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/accordes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(accorde: IAccorde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accorde);
    return this.http
      .post<IAccorde>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accorde: IAccorde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accorde);
    return this.http
      .put<IAccorde>(`${this.resourceUrl}/${getAccordeIdentifier(accorde) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(accorde: IAccorde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accorde);
    return this.http
      .patch<IAccorde>(`${this.resourceUrl}/${getAccordeIdentifier(accorde) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccorde>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccorde[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAccordeToCollectionIfMissing(accordeCollection: IAccorde[], ...accordesToCheck: (IAccorde | null | undefined)[]): IAccorde[] {
    const accordes: IAccorde[] = accordesToCheck.filter(isPresent);
    if (accordes.length > 0) {
      const accordeCollectionIdentifiers = accordeCollection.map(accordeItem => getAccordeIdentifier(accordeItem)!);
      const accordesToAdd = accordes.filter(accordeItem => {
        const accordeIdentifier = getAccordeIdentifier(accordeItem);
        if (accordeIdentifier == null || accordeCollectionIdentifiers.includes(accordeIdentifier)) {
          return false;
        }
        accordeCollectionIdentifiers.push(accordeIdentifier);
        return true;
      });
      return [...accordesToAdd, ...accordeCollection];
    }
    return accordeCollection;
  }

  protected convertDateFromClient(accorde: IAccorde): IAccorde {
    return Object.assign({}, accorde, {
      dateAccord: accorde.dateAccord?.isValid() ? accorde.dateAccord.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAccord = res.body.dateAccord ? dayjs(res.body.dateAccord) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accorde: IAccorde) => {
        accorde.dateAccord = accorde.dateAccord ? dayjs(accorde.dateAccord) : undefined;
      });
    }
    return res;
  }
}
