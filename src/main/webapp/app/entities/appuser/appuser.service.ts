import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAppuser } from 'app/shared/model/appuser.model';

type EntityResponseType = HttpResponse<IAppuser>;
type EntityArrayResponseType = HttpResponse<IAppuser[]>;

@Injectable({ providedIn: 'root' })
export class AppuserService {
  public resourceUrl = SERVER_API_URL + 'api/appusers';

  constructor(protected http: HttpClient) {}

  create(appuser: IAppuser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(appuser);
    return this.http
      .post<IAppuser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(appuser: IAppuser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(appuser);
    return this.http
      .put<IAppuser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAppuser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAppuser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(appuser: IAppuser): IAppuser {
    const copy: IAppuser = Object.assign({}, appuser, {
      creationDate: appuser.creationDate != null && appuser.creationDate.isValid() ? appuser.creationDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationDate = res.body.creationDate != null ? moment(res.body.creationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((appuser: IAppuser) => {
        appuser.creationDate = appuser.creationDate != null ? moment(appuser.creationDate) : null;
      });
    }
    return res;
  }
}
