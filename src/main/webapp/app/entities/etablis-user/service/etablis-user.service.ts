import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEtablisUser, getEtablisUserIdentifier } from '../etablis-user.model';

export type EntityResponseType = HttpResponse<IEtablisUser>;
export type EntityArrayResponseType = HttpResponse<IEtablisUser[]>;

@Injectable({ providedIn: 'root' })
export class EtablisUserService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/etablis-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(etablisUser: IEtablisUser): Observable<EntityResponseType> {
    return this.http.post<IEtablisUser>(this.resourceUrl, etablisUser, { observe: 'response' });
  }

  update(etablisUser: IEtablisUser): Observable<EntityResponseType> {
    return this.http.put<IEtablisUser>(`${this.resourceUrl}/${getEtablisUserIdentifier(etablisUser) as number}`, etablisUser, {
      observe: 'response',
    });
  }

  partialUpdate(etablisUser: IEtablisUser): Observable<EntityResponseType> {
    return this.http.patch<IEtablisUser>(`${this.resourceUrl}/${getEtablisUserIdentifier(etablisUser) as number}`, etablisUser, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtablisUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtablisUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEtablisUserToCollectionIfMissing(
    etablisUserCollection: IEtablisUser[],
    ...etablisUsersToCheck: (IEtablisUser | null | undefined)[]
  ): IEtablisUser[] {
    const etablisUsers: IEtablisUser[] = etablisUsersToCheck.filter(isPresent);
    if (etablisUsers.length > 0) {
      const etablisUserCollectionIdentifiers = etablisUserCollection.map(etablisUserItem => getEtablisUserIdentifier(etablisUserItem)!);
      const etablisUsersToAdd = etablisUsers.filter(etablisUserItem => {
        const etablisUserIdentifier = getEtablisUserIdentifier(etablisUserItem);
        if (etablisUserIdentifier == null || etablisUserCollectionIdentifiers.includes(etablisUserIdentifier)) {
          return false;
        }
        etablisUserCollectionIdentifiers.push(etablisUserIdentifier);
        return true;
      });
      return [...etablisUsersToAdd, ...etablisUserCollection];
    }
    return etablisUserCollection;
  }
}
