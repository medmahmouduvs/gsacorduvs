import { IUser } from 'app/entities/user/user.model';

export interface IEspaceAcEtEl {
  id?: number;
  name?: string;
  handle?: string;
  user?: IUser | null;
}

export class EspaceAcEtEl implements IEspaceAcEtEl {
  constructor(public id?: number, public name?: string, public handle?: string, public user?: IUser | null) {}
}

export function getEspaceAcEtElIdentifier(espaceAcEtEl: IEspaceAcEtEl): number | undefined {
  return espaceAcEtEl.id;
}
