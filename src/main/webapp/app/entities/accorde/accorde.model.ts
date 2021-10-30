import * as dayjs from 'dayjs';
import { IEspaceAcEtEl } from 'app/entities/espace-ac-et-el/espace-ac-et-el.model';
import { IEtablisemntParten } from 'app/entities/etablisemnt-parten/etablisemnt-parten.model';
import { IEtudeAccord } from 'app/entities/etude-accord/etude-accord.model';
import { Teritoir } from 'app/entities/enumerations/teritoir.model';
import { Statueoption } from 'app/entities/enumerations/statueoption.model';

export interface IAccorde {
  id?: number;
  titre?: string | null;
  teritornature?: Teritoir | null;
  statusacord?: Statueoption | null;
  dateAccord?: dayjs.Dayjs | null;
  signaturereacteru?: boolean | null;
  signatureDiircore?: boolean | null;
  signatureChefEtab?: boolean | null;
  article?: string | null;
  espaceAcEtEl?: IEspaceAcEtEl | null;
  etablisemntPartens?: IEtablisemntParten[] | null;
  etudeAccords?: IEtudeAccord[] | null;
}

export class Accorde implements IAccorde {
  constructor(
    public id?: number,
    public titre?: string | null,
    public teritornature?: Teritoir | null,
    public statusacord?: Statueoption | null,
    public dateAccord?: dayjs.Dayjs | null,
    public signaturereacteru?: boolean | null,
    public signatureDiircore?: boolean | null,
    public signatureChefEtab?: boolean | null,
    public article?: string | null,
    public espaceAcEtEl?: IEspaceAcEtEl | null,
    public etablisemntPartens?: IEtablisemntParten[] | null,
    public etudeAccords?: IEtudeAccord[] | null
  ) {
    this.signaturereacteru = this.signaturereacteru ?? false;
    this.signatureDiircore = this.signatureDiircore ?? false;
    this.signatureChefEtab = this.signatureChefEtab ?? false;
  }
}

export function getAccordeIdentifier(accorde: IAccorde): number | undefined {
  return accorde.id;
}
