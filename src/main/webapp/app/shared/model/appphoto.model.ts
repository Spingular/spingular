import { Moment } from 'moment';

export interface IAppphoto {
  id?: number;
  creationDate?: Moment;
  imageContentType?: string;
  image?: any;
  appuserId?: number;
}

export class Appphoto implements IAppphoto {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public imageContentType?: string,
    public image?: any,
    public appuserId?: number
  ) {}
}
