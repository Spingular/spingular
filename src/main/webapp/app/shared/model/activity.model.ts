import { IAppuser } from 'app/shared/model/appuser.model';

export interface IActivity {
  id?: number;
  activityName?: string;
  appusers?: IAppuser[];
}

export class Activity implements IActivity {
  constructor(public id?: number, public activityName?: string, public appusers?: IAppuser[]) {}
}
