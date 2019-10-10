import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAppphoto } from 'app/shared/model/appphoto.model';

type EntityResponseType = HttpResponse<IAppphoto>;
type EntityArrayResponseType = HttpResponse<IAppphoto[]>;

@Injectable({ providedIn: 'root' })
export class AppphotoService {
  public resourceUrl = SERVER_API_URL + 'api/appphotos';

  constructor(protected http: HttpClient) {}

  create(appphoto: IAppphoto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(appphoto);
    return this.http
      .post<IAppphoto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(appphoto: IAppphoto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(appphoto);
    return this.http
      .put<IAppphoto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAppphoto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAppphoto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(appphoto: IAppphoto): IAppphoto {
    const copy: IAppphoto = Object.assign({}, appphoto, {
      creationDate: appphoto.creationDate != null && appphoto.creationDate.isValid() ? appphoto.creationDate.toJSON() : null
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
      res.body.forEach((appphoto: IAppphoto) => {
        appphoto.creationDate = appphoto.creationDate != null ? moment(appphoto.creationDate) : null;
      });
    }
    return res;
  }
}
