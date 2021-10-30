import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEtablisemntParten, getEtablisemntPartenIdentifier } from '../etablisemnt-parten.model';

export type EntityResponseType = HttpResponse<IEtablisemntParten>;
export type EntityArrayResponseType = HttpResponse<IEtablisemntParten[]>;

@Injectable({ providedIn: 'root' })
export class EtablisemntPartenService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/etablisemnt-partens');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(etablisemntParten: IEtablisemntParten): Observable<EntityResponseType> {
    return this.http.post<IEtablisemntParten>(this.resourceUrl, etablisemntParten, { observe: 'response' });
  }

  update(etablisemntParten: IEtablisemntParten): Observable<EntityResponseType> {
    return this.http.put<IEtablisemntParten>(
      `${this.resourceUrl}/${getEtablisemntPartenIdentifier(etablisemntParten) as number}`,
      etablisemntParten,
      { observe: 'response' }
    );
  }

  partialUpdate(etablisemntParten: IEtablisemntParten): Observable<EntityResponseType> {
    return this.http.patch<IEtablisemntParten>(
      `${this.resourceUrl}/${getEtablisemntPartenIdentifier(etablisemntParten) as number}`,
      etablisemntParten,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtablisemntParten>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtablisemntParten[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEtablisemntPartenToCollectionIfMissing(
    etablisemntPartenCollection: IEtablisemntParten[],
    ...etablisemntPartensToCheck: (IEtablisemntParten | null | undefined)[]
  ): IEtablisemntParten[] {
    const etablisemntPartens: IEtablisemntParten[] = etablisemntPartensToCheck.filter(isPresent);
    if (etablisemntPartens.length > 0) {
      const etablisemntPartenCollectionIdentifiers = etablisemntPartenCollection.map(
        etablisemntPartenItem => getEtablisemntPartenIdentifier(etablisemntPartenItem)!
      );
      const etablisemntPartensToAdd = etablisemntPartens.filter(etablisemntPartenItem => {
        const etablisemntPartenIdentifier = getEtablisemntPartenIdentifier(etablisemntPartenItem);
        if (etablisemntPartenIdentifier == null || etablisemntPartenCollectionIdentifiers.includes(etablisemntPartenIdentifier)) {
          return false;
        }
        etablisemntPartenCollectionIdentifiers.push(etablisemntPartenIdentifier);
        return true;
      });
      return [...etablisemntPartensToAdd, ...etablisemntPartenCollection];
    }
    return etablisemntPartenCollection;
  }
}
