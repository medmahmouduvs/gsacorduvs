import { IAccorde } from 'app/entities/accorde/accorde.model';
import { IDemandElaboration } from 'app/entities/demand-elaboration/demand-elaboration.model';

export interface IEtablisemntParten {
  id?: number;
  contry?: string | null;
  nameEtab?: string | null;
  domain?: string | null;
  mention?: string | null;
  representantname?: string | null;
  accordes?: IAccorde[] | null;
  demandElaborations?: IDemandElaboration[] | null;
}

export class EtablisemntParten implements IEtablisemntParten {
  constructor(
    public id?: number,
    public contry?: string | null,
    public nameEtab?: string | null,
    public domain?: string | null,
    public mention?: string | null,
    public representantname?: string | null,
    public accordes?: IAccorde[] | null,
    public demandElaborations?: IDemandElaboration[] | null
  ) {}
}

export function getEtablisemntPartenIdentifier(etablisemntParten: IEtablisemntParten): number | undefined {
  return etablisemntParten.id;
}
