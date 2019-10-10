import { Moment } from 'moment';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { CivilStatus } from 'app/shared/model/enumerations/civil-status.model';
import { Purpose } from 'app/shared/model/enumerations/purpose.model';
import { Physical } from 'app/shared/model/enumerations/physical.model';
import { Religion } from 'app/shared/model/enumerations/religion.model';
import { EthnicGroup } from 'app/shared/model/enumerations/ethnic-group.model';
import { Studies } from 'app/shared/model/enumerations/studies.model';
import { Eyes } from 'app/shared/model/enumerations/eyes.model';
import { Smoker } from 'app/shared/model/enumerations/smoker.model';
import { Children } from 'app/shared/model/enumerations/children.model';
import { FutureChildren } from 'app/shared/model/enumerations/future-children.model';

export interface IAppprofile {
  id?: number;
  creationDate?: Moment;
  gender?: Gender;
  phone?: string;
  bio?: string;
  facebook?: string;
  twitter?: string;
  linkedin?: string;
  instagram?: string;
  googlePlus?: string;
  birthdate?: Moment;
  civilStatus?: CivilStatus;
  lookingFor?: Gender;
  purpose?: Purpose;
  physical?: Physical;
  religion?: Religion;
  ethnicGroup?: EthnicGroup;
  studies?: Studies;
  sibblings?: number;
  eyes?: Eyes;
  smoker?: Smoker;
  children?: Children;
  futureChildren?: FutureChildren;
  pet?: boolean;
  appuserId?: number;
}

export class Appprofile implements IAppprofile {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public gender?: Gender,
    public phone?: string,
    public bio?: string,
    public facebook?: string,
    public twitter?: string,
    public linkedin?: string,
    public instagram?: string,
    public googlePlus?: string,
    public birthdate?: Moment,
    public civilStatus?: CivilStatus,
    public lookingFor?: Gender,
    public purpose?: Purpose,
    public physical?: Physical,
    public religion?: Religion,
    public ethnicGroup?: EthnicGroup,
    public studies?: Studies,
    public sibblings?: number,
    public eyes?: Eyes,
    public smoker?: Smoker,
    public children?: Children,
    public futureChildren?: FutureChildren,
    public pet?: boolean,
    public appuserId?: number
  ) {
    this.pet = this.pet || false;
  }
}
