import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUrllink } from 'app/shared/model/urllink.model';

type EntityResponseType = HttpResponse<IUrllink>;
type EntityArrayResponseType = HttpResponse<IUrllink[]>;

@Injectable({ providedIn: 'root' })
export class UrllinkService {
  public resourceUrl = SERVER_API_URL + 'api/urllinks';

  constructor(protected http: HttpClient) {}

  create(urllink: IUrllink): Observable<EntityResponseType> {
    return this.http.post<IUrllink>(this.resourceUrl, urllink, { observe: 'response' });
  }

  update(urllink: IUrllink): Observable<EntityResponseType> {
    return this.http.put<IUrllink>(this.resourceUrl, urllink, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUrllink>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUrllink[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
