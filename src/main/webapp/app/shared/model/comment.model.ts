import { Moment } from 'moment';

export interface IComment {
  id?: number;
  creationDate?: Moment;
  commentText?: string;
  isOffensive?: boolean;
  appuserId?: number;
  postId?: number;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public commentText?: string,
    public isOffensive?: boolean,
    public appuserId?: number,
    public postId?: number
  ) {
    this.isOffensive = this.isOffensive || false;
  }
}
