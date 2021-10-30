import * as dayjs from 'dayjs';
import { IEspaceAcEtEl } from 'app/entities/espace-ac-et-el/espace-ac-et-el.model';
import { IAccorde } from 'app/entities/accorde/accorde.model';

export interface IEtudeAccord {
  id?: number;
  titre?: string | null;
  dateEtude?: dayjs.Dayjs | null;
  motiveDirCoor?: string | null;
  signaturereacteru?: boolean | null;
  signatureDiircore?: boolean | null;
  signatureChefEtab?: boolean | null;
  espaceAcEtEl?: IEspaceAcEtEl | null;
  accordes?: IAccorde[] | null;
}

export class EtudeAccord implements IEtudeAccord {
  constructor(
    public id?: number,
    public titre?: string | null,
    public dateEtude?: dayjs.Dayjs | null,
    public motiveDirCoor?: string | null,
    public signaturereacteru?: boolean | null,
    public signatureDiircore?: boolean | null,
    public signatureChefEtab?: boolean | null,
    public espaceAcEtEl?: IEspaceAcEtEl | null,
    public accordes?: IAccorde[] | null
  ) {
    this.signaturereacteru = this.signaturereacteru ?? false;
    this.signatureDiircore = this.signatureDiircore ?? false;
    this.signatureChefEtab = this.signatureChefEtab ?? false;
  }
}

export function getEtudeAccordIdentifier(etudeAccord: IEtudeAccord): number | undefined {
  return etudeAccord.id;
}
