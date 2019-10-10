import { Moment } from 'moment';
import { NotificationReason } from 'app/shared/model/enumerations/notification-reason.model';

export interface INotification {
  id?: number;
  creationDate?: Moment;
  notificationDate?: Moment;
  notificationReason?: NotificationReason;
  notificationText?: string;
  isDelivered?: boolean;
  appuserId?: number;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public notificationDate?: Moment,
    public notificationReason?: NotificationReason,
    public notificationText?: string,
    public isDelivered?: boolean,
    public appuserId?: number
  ) {
    this.isDelivered = this.isDelivered || false;
  }
}
