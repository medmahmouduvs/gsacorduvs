import { IUser } from 'app/entities/user/user.model';

export interface IEtablisUser {
  id?: number;
  nome?: string | null;
  email?: string | null;
  position?: string | null;
  username?: string | null;
  password?: string | null;
  internalUser?: IUser | null;
}

export class EtablisUser implements IEtablisUser {
  constructor(
    public id?: number,
    public nome?: string | null,
    public email?: string | null,
    public position?: string | null,
    public username?: string | null,
    public password?: string | null,
    public internalUser?: IUser | null
  ) {}
}

export function getEtablisUserIdentifier(etablisUser: IEtablisUser): number | undefined {
  return etablisUser.id;
}
