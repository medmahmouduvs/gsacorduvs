import * as dayjs from 'dayjs';
import { IEspaceAcEtEl } from 'app/entities/espace-ac-et-el/espace-ac-et-el.model';
import { IEtablisemntParten } from 'app/entities/etablisemnt-parten/etablisemnt-parten.model';
import { TypeAccord } from 'app/entities/enumerations/type-accord.model';

export interface IDemandElaboration {
  id?: number;
  typeAccord?: TypeAccord | null;
  titreDemand?: string | null;
  dateDeman?: dayjs.Dayjs | null;
  formeAccord?: string | null;
  signaturedircoor?: boolean | null;
  espaceAcEtEl?: IEspaceAcEtEl | null;
  etablisemntPartens?: IEtablisemntParten[] | null;
}

export class DemandElaboration implements IDemandElaboration {
  constructor(
    public id?: number,
    public typeAccord?: TypeAccord | null,
    public titreDemand?: string | null,
    public dateDeman?: dayjs.Dayjs | null,
    public formeAccord?: string | null,
    public signaturedircoor?: boolean | null,
    public espaceAcEtEl?: IEspaceAcEtEl | null,
    public etablisemntPartens?: IEtablisemntParten[] | null
  ) {
    this.signaturedircoor = this.signaturedircoor ?? false;
  }
}

export function getDemandElaborationIdentifier(demandElaboration: IDemandElaboration): number | undefined {
  return demandElaboration.id;
}
