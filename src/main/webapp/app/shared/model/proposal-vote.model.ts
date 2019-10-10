import { Moment } from 'moment';

export interface IProposalVote {
  id?: number;
  creationDate?: Moment;
  votePoints?: number;
  appuserId?: number;
  proposalId?: number;
}

export class ProposalVote implements IProposalVote {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public votePoints?: number,
    public appuserId?: number,
    public proposalId?: number
  ) {}
}
