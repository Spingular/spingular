import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProposal, Proposal } from 'app/shared/model/proposal.model';
import { ProposalService } from './proposal.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post/post.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-proposal-update',
  templateUrl: './proposal-update.component.html'
})
export class ProposalUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  posts: IPost[];

  appuser: IAppuser;
  owner: any;
  isAdmin: boolean;
  currentAccount: any;
  creationDate: string;

  private _proposal: IProposal;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    proposalName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(250)]],
    proposalType: [null, [Validators.required]],
    proposalRole: [null, [Validators.required]],
    releaseDate: [],
    isOpen: [],
    isAccepted: [],
    isPaid: [],
    appuserId: [],
    postId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected proposalService: ProposalService,
    protected appuserService: AppuserService,
    protected postService: PostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proposal }) => {
      this.proposal = proposal;
      this.creationDate = moment().format(DATE_TIME_FORMAT);
      this.proposal.creationDate = moment(this.creationDate);
      this.proposal.releaseDate = moment(this.creationDate);
      this.updateForm(proposal);
    });
    this.accountService.identity().subscribe(
      account => {
        this.currentAccount = account;
        this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
        const query = {};
        if (this.currentAccount.id != null) {
          query['userId.equals'] = this.currentAccount.id;
        }
        this.appuserService.query(query).subscribe((res: HttpResponse<IAppuser[]>) => {
          // this.owner = res.body[0].id;
          this.appusers = res.body;
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.postService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPost[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPost[]>) => response.body)
      )
      .subscribe((res: IPost[]) => (this.posts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(proposal: IProposal) {
    this.editForm.patchValue({
      id: proposal.id,
      creationDate: proposal.creationDate != null ? proposal.creationDate.format(DATE_TIME_FORMAT) : null,
      proposalName: proposal.proposalName,
      proposalType: proposal.proposalType,
      proposalRole: proposal.proposalRole,
      releaseDate: proposal.releaseDate != null ? proposal.releaseDate.format(DATE_TIME_FORMAT) : null,
      isOpen: proposal.isOpen,
      isAccepted: proposal.isAccepted,
      isPaid: proposal.isPaid,
      appuserId: proposal.appuserId,
      postId: proposal.postId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const proposal = this.createFromForm();
    if (proposal.id !== undefined) {
      this.subscribeToSaveResponse(this.proposalService.update(proposal));
    } else {
      this.subscribeToSaveResponse(this.proposalService.create(proposal));
    }
  }

  private createFromForm(): IProposal {
    return {
      ...new Proposal(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      proposalName: this.editForm.get(['proposalName']).value,
      proposalType: this.editForm.get(['proposalType']).value,
      proposalRole: this.editForm.get(['proposalRole']).value,
      releaseDate:
        this.editForm.get(['releaseDate']).value != null ? moment(this.editForm.get(['releaseDate']).value, DATE_TIME_FORMAT) : undefined,
      isOpen: this.editForm.get(['isOpen']).value,
      isAccepted: this.editForm.get(['isAccepted']).value,
      isPaid: this.editForm.get(['isPaid']).value,
      appuserId: this.editForm.get(['appuserId']).value,
      postId: this.editForm.get(['postId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProposal>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAppuserById(index: number, item: IAppuser) {
    return item.id;
  }

  trackPostById(index: number, item: IPost) {
    return item.id;
  }

  get proposal() {
    return this._proposal;
  }

  set proposal(proposal: IProposal) {
    this._proposal = proposal;
    this.creationDate = moment(proposal.creationDate).format(DATE_TIME_FORMAT);
  }
}
