import { IAppuser } from 'app/shared/model/appuser.model';

export interface ICeleb {
  id?: number;
  celebName?: string;
  appusers?: IAppuser[];
}

export class Celeb implements ICeleb {
  constructor(public id?: number, public celebName?: string, public appusers?: IAppuser[]) {}
}
