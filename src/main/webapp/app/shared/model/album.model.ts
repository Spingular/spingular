import { Moment } from 'moment';
import { IPhoto } from 'app/shared/model/photo.model';

export interface IAlbum {
  id?: number;
  creationDate?: Moment;
  title?: string;
  photos?: IPhoto[];
  appuserId?: number;
}

export class Album implements IAlbum {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public title?: string,
    public photos?: IPhoto[],
    public appuserId?: number
  ) {}
}
