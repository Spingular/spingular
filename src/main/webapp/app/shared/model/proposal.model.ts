import { Moment } from 'moment';
import { IProposalVote } from 'app/shared/model/proposal-vote.model';
import { ProposalType } from 'app/shared/model/enumerations/proposal-type.model';
import { ProposalRole } from 'app/shared/model/enumerations/proposal-role.model';

export interface IProposal {
  id?: number;
  creationDate?: Moment;
  proposalName?: string;
  proposalType?: ProposalType;
  proposalRole?: ProposalRole;
  releaseDate?: Moment;
  isOpen?: boolean;
  isAccepted?: boolean;
  isPaid?: boolean;
  proposalVotes?: IProposalVote[];
  appuserId?: number;
  postId?: number;
}

export class Proposal implements IProposal {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public proposalName?: string,
    public proposalType?: ProposalType,
    public proposalRole?: ProposalRole,
    public releaseDate?: Moment,
    public isOpen?: boolean,
    public isAccepted?: boolean,
    public isPaid?: boolean,
    public proposalVotes?: IProposalVote[],
    public appuserId?: number,
    public postId?: number
  ) {
    this.isOpen = this.isOpen || false;
    this.isAccepted = this.isAccepted || false;
    this.isPaid = this.isPaid || false;
  }
}
