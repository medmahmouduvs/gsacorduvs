import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemandElaboration, getDemandElaborationIdentifier } from '../demand-elaboration.model';

export type EntityResponseType = HttpResponse<IDemandElaboration>;
export type EntityArrayResponseType = HttpResponse<IDemandElaboration[]>;

@Injectable({ providedIn: 'root' })
export class DemandElaborationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demand-elaborations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(demandElaboration: IDemandElaboration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandElaboration);
    return this.http
      .post<IDemandElaboration>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(demandElaboration: IDemandElaboration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandElaboration);
    return this.http
      .put<IDemandElaboration>(`${this.resourceUrl}/${getDemandElaborationIdentifier(demandElaboration) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(demandElaboration: IDemandElaboration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(demandElaboration);
    return this.http
      .patch<IDemandElaboration>(`${this.resourceUrl}/${getDemandElaborationIdentifier(demandElaboration) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDemandElaboration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDemandElaboration[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDemandElaborationToCollectionIfMissing(
    demandElaborationCollection: IDemandElaboration[],
    ...demandElaborationsToCheck: (IDemandElaboration | null | undefined)[]
  ): IDemandElaboration[] {
    const demandElaborations: IDemandElaboration[] = demandElaborationsToCheck.filter(isPresent);
    if (demandElaborations.length > 0) {
      const demandElaborationCollectionIdentifiers = demandElaborationCollection.map(
        demandElaborationItem => getDemandElaborationIdentifier(demandElaborationItem)!
      );
      const demandElaborationsToAdd = demandElaborations.filter(demandElaborationItem => {
        const demandElaborationIdentifier = getDemandElaborationIdentifier(demandElaborationItem);
        if (demandElaborationIdentifier == null || demandElaborationCollectionIdentifiers.includes(demandElaborationIdentifier)) {
          return false;
        }
        demandElaborationCollectionIdentifiers.push(demandElaborationIdentifier);
        return true;
      });
      return [...demandElaborationsToAdd, ...demandElaborationCollection];
    }
    return demandElaborationCollection;
  }

  protected convertDateFromClient(demandElaboration: IDemandElaboration): IDemandElaboration {
    return Object.assign({}, demandElaboration, {
      dateDeman: demandElaboration.dateDeman?.isValid() ? demandElaboration.dateDeman.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDeman = res.body.dateDeman ? dayjs(res.body.dateDeman) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((demandElaboration: IDemandElaboration) => {
        demandElaboration.dateDeman = demandElaboration.dateDeman ? dayjs(demandElaboration.dateDeman) : undefined;
      });
    }
    return res;
  }
}
