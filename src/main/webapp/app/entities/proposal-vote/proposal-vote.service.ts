import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProposalVote } from 'app/shared/model/proposal-vote.model';

type EntityResponseType = HttpResponse<IProposalVote>;
type EntityArrayResponseType = HttpResponse<IProposalVote[]>;

@Injectable({ providedIn: 'root' })
export class ProposalVoteService {
  public resourceUrl = SERVER_API_URL + 'api/proposal-votes';

  constructor(protected http: HttpClient) {}

  create(proposalVote: IProposalVote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proposalVote);
    return this.http
      .post<IProposalVote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(proposalVote: IProposalVote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proposalVote);
    return this.http
      .put<IProposalVote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProposalVote>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProposalVote[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(proposalVote: IProposalVote): IProposalVote {
    const copy: IProposalVote = Object.assign({}, proposalVote, {
      creationDate: proposalVote.creationDate != null && proposalVote.creationDate.isValid() ? proposalVote.creationDate.toJSON() : null
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
      res.body.forEach((proposalVote: IProposalVote) => {
        proposalVote.creationDate = proposalVote.creationDate != null ? moment(proposalVote.creationDate) : null;
      });
    }
    return res;
  }
}
