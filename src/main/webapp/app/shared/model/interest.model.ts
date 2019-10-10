import { IAppuser } from 'app/shared/model/appuser.model';

export interface IInterest {
  id?: number;
  interestName?: string;
  appusers?: IAppuser[];
}

export class Interest implements IInterest {
  constructor(public id?: number, public interestName?: string, public appusers?: IAppuser[]) {}
}
