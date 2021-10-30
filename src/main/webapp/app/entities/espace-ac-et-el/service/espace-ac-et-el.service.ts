import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEspaceAcEtEl, getEspaceAcEtElIdentifier } from '../espace-ac-et-el.model';

export type EntityResponseType = HttpResponse<IEspaceAcEtEl>;
export type EntityArrayResponseType = HttpResponse<IEspaceAcEtEl[]>;

@Injectable({ providedIn: 'root' })
export class EspaceAcEtElService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/espace-ac-et-els');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(espaceAcEtEl: IEspaceAcEtEl): Observable<EntityResponseType> {
    return this.http.post<IEspaceAcEtEl>(this.resourceUrl, espaceAcEtEl, { observe: 'response' });
  }

  update(espaceAcEtEl: IEspaceAcEtEl): Observable<EntityResponseType> {
    return this.http.put<IEspaceAcEtEl>(`${this.resourceUrl}/${getEspaceAcEtElIdentifier(espaceAcEtEl) as number}`, espaceAcEtEl, {
      observe: 'response',
    });
  }

  partialUpdate(espaceAcEtEl: IEspaceAcEtEl): Observable<EntityResponseType> {
    return this.http.patch<IEspaceAcEtEl>(`${this.resourceUrl}/${getEspaceAcEtElIdentifier(espaceAcEtEl) as number}`, espaceAcEtEl, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEspaceAcEtEl>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEspaceAcEtEl[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEspaceAcEtElToCollectionIfMissing(
    espaceAcEtElCollection: IEspaceAcEtEl[],
    ...espaceAcEtElsToCheck: (IEspaceAcEtEl | null | undefined)[]
  ): IEspaceAcEtEl[] {
    const espaceAcEtEls: IEspaceAcEtEl[] = espaceAcEtElsToCheck.filter(isPresent);
    if (espaceAcEtEls.length > 0) {
      const espaceAcEtElCollectionIdentifiers = espaceAcEtElCollection.map(
        espaceAcEtElItem => getEspaceAcEtElIdentifier(espaceAcEtElItem)!
      );
      const espaceAcEtElsToAdd = espaceAcEtEls.filter(espaceAcEtElItem => {
        const espaceAcEtElIdentifier = getEspaceAcEtElIdentifier(espaceAcEtElItem);
        if (espaceAcEtElIdentifier == null || espaceAcEtElCollectionIdentifiers.includes(espaceAcEtElIdentifier)) {
          return false;
        }
        espaceAcEtElCollectionIdentifiers.push(espaceAcEtElIdentifier);
        return true;
      });
      return [...espaceAcEtElsToAdd, ...espaceAcEtElCollection];
    }
    return espaceAcEtElCollection;
  }
}
