import { Moment } from 'moment';
import { ICommunity } from 'app/shared/model/community.model';
import { IBlog } from 'app/shared/model/blog.model';
import { INotification } from 'app/shared/model/notification.model';
import { IAlbum } from 'app/shared/model/album.model';
import { IComment } from 'app/shared/model/comment.model';
import { IPost } from 'app/shared/model/post.model';
import { IMessage } from 'app/shared/model/message.model';
import { IFollow } from 'app/shared/model/follow.model';
import { IBlockuser } from 'app/shared/model/blockuser.model';
import { IVtopic } from 'app/shared/model/vtopic.model';
import { IVquestion } from 'app/shared/model/vquestion.model';
import { IVanswer } from 'app/shared/model/vanswer.model';
import { IVthumb } from 'app/shared/model/vthumb.model';
import { IProposal } from 'app/shared/model/proposal.model';
import { IProposalVote } from 'app/shared/model/proposal-vote.model';
import { IInterest } from 'app/shared/model/interest.model';
import { IActivity } from 'app/shared/model/activity.model';
import { ICeleb } from 'app/shared/model/celeb.model';

export interface IAppuser {
  id?: number;
  creationDate?: Moment;
  assignedVotesPoints?: number;
  userId?: number;
  appprofileId?: number;
  appphotoId?: number;
  communities?: ICommunity[];
  blogs?: IBlog[];
  notifications?: INotification[];
  albums?: IAlbum[];
  comments?: IComment[];
  posts?: IPost[];
  senders?: IMessage[];
  receivers?: IMessage[];
  followeds?: IFollow[];
  followings?: IFollow[];
  blockedusers?: IBlockuser[];
  blockingusers?: IBlockuser[];
  vtopics?: IVtopic[];
  vquestions?: IVquestion[];
  vanswers?: IVanswer[];
  vthumbs?: IVthumb[];
  proposals?: IProposal[];
  proposalVotes?: IProposalVote[];
  interests?: IInterest[];
  activities?: IActivity[];
  celebs?: ICeleb[];
}

export class Appuser implements IAppuser {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public assignedVotesPoints?: number,
    public userId?: number,
    public appprofileId?: number,
    public appphotoId?: number,
    public communities?: ICommunity[],
    public blogs?: IBlog[],
    public notifications?: INotification[],
    public albums?: IAlbum[],
    public comments?: IComment[],
    public posts?: IPost[],
    public senders?: IMessage[],
    public receivers?: IMessage[],
    public followeds?: IFollow[],
    public followings?: IFollow[],
    public blockedusers?: IBlockuser[],
    public blockingusers?: IBlockuser[],
    public vtopics?: IVtopic[],
    public vquestions?: IVquestion[],
    public vanswers?: IVanswer[],
    public vthumbs?: IVthumb[],
    public proposals?: IProposal[],
    public proposalVotes?: IProposalVote[],
    public interests?: IInterest[],
    public activities?: IActivity[],
    public celebs?: ICeleb[]
  ) {}
}
