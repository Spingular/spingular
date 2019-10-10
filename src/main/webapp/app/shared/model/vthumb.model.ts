import { Moment } from 'moment';

export interface IVthumb {
  id?: number;
  creationDate?: Moment;
  vthumbUp?: boolean;
  vthumbDown?: boolean;
  appuserId?: number;
  vquestionId?: number;
  vanswerId?: number;
}

export class Vthumb implements IVthumb {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public vthumbUp?: boolean,
    public vthumbDown?: boolean,
    public appuserId?: number,
    public vquestionId?: number,
    public vanswerId?: number
  ) {
    this.vthumbUp = this.vthumbUp || false;
    this.vthumbDown = this.vthumbDown || false;
  }
}
