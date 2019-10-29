import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IVtopic, Vtopic } from 'app/shared/model/vtopic.model';
import { VtopicService } from './vtopic.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-vtopic-update',
  templateUrl: './vtopic-update.component.html'
})
export class VtopicUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];
  appuser: IAppuser;

  owner: any;
  isAdmin: boolean;
  creationDate: string;
  currentAccount: any;

  private _vtopic: IVtopic;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    vtopicTitle: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
    vtopicDescription: [null, [Validators.minLength(2), Validators.maxLength(250)]],
    appuserId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vtopicService: VtopicService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vtopic }) => {
      this.vtopic = vtopic;
      this.creationDate = moment().format(DATE_TIME_FORMAT);
      this.vtopic.creationDate = moment(this.creationDate);
      this.updateForm(vtopic);
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
          this.appusers = res.body;
          this.owner = res.body[0].id;
          // this.loggedUser = res.body[0];
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    // this.appuserService
    //   .query()
    //   .pipe(
    //     filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
    //     map((response: HttpResponse<IAppuser[]>) => response.body)
    //   )
    //   .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vtopic: IVtopic) {
    this.editForm.patchValue({
      id: vtopic.id,
      creationDate: vtopic.creationDate != null ? vtopic.creationDate.format(DATE_TIME_FORMAT) : null,
      vtopicTitle: vtopic.vtopicTitle,
      vtopicDescription: vtopic.vtopicDescription,
      appuserId: vtopic.appuserId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vtopic = this.createFromForm();
    if (vtopic.id !== undefined) {
      this.subscribeToSaveResponse(this.vtopicService.update(vtopic));
    } else {
      this.subscribeToSaveResponse(this.vtopicService.create(vtopic));
    }
  }

  private createFromForm(): IVtopic {
    return {
      ...new Vtopic(),
      id: this.editForm.get(['id']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      vtopicTitle: this.editForm.get(['vtopicTitle']).value,
      vtopicDescription: this.editForm.get(['vtopicDescription']).value,
      appuserId: this.editForm.get(['appuserId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVtopic>>) {
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

  get vtopic() {
    return this._vtopic;
  }

  set vtopic(vtopic: IVtopic) {
    this._vtopic = vtopic;
    this.creationDate = moment(vtopic.creationDate).format(DATE_TIME_FORMAT);
  }
}
