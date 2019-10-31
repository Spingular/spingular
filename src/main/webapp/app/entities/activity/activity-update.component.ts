import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { IActivity, Activity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-update.component.html'
})
export class ActivityUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  activity: IActivity;
  activities: IActivity[];

  isCreateDisabled = false;

  nameParamUprofileId: any;
  valueParamUprofileId: any;

  currentAccount: any;
  currentSearch: string;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any = 1;
  predicate: any = 'id';
  previousPage: any = 0;
  reverse: any = 'asc';
  id: any;
  owner: any;
  isAdmin: boolean;
  editForm = this.fb.group({
    id: [],
    activityName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    appusers: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activityService: ActivityService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected router: Router,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    protected eventManager: JhiEventManager
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.uprofileIdEquals != null) {
        this.nameParamUprofileId = 'uprofile.userId';
        this.valueParamUprofileId = params.uprofileIdEquals;
      }
    });
  }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ activity }) => {
      this.updateForm(activity);
      this.activity = activity;
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
          this.owner = res.body[0].id;
          this.appusers = res.body;
          this.myUserActivities(this.owner);
          // this.loggedUser = res.body[0];
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private myUserActivities(owner) {
    const query = {};
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.owner;
    }
    this.activityService.query(query).subscribe(
      (res: HttpResponse<IActivity[]>) => {
        this.activities = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  loadAll() {
    if (this.currentSearch) {
      this.activityService
        .query({
          page: this.page - 1,
          'activityName.contains': this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IActivity[]>) => this.paginateActivities(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.activityService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IActivity[]>) => this.paginateActivities(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  addExistingProfileActivity(activityId) {
    this.isSaving = true;
    if (activityId !== undefined) {
      const query = {};
      query['id.equals'] = activityId;
      this.activityService.query(query).subscribe(
        (res: HttpResponse<IActivity[]>) => {
          this.activities = res.body;
          this.activities[0].appusers.push(this.appusers[0]);
          this.subscribeToSaveResponse(this.activityService.update(this.activities[0]));
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/activity/new'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  search(query: string) {
    this.isCreateDisabled = true;
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/activity/new',
      {
        search: this.currentSearch,
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.currentSearch = '';
    this.router.navigate([
      '/activity/new',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  private paginateActivities(data: IActivity[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.activities = data;
    if (this.totalItems === 0) {
      this.activity.activityName = this.currentSearch;
    }
  }

  updateForm(activity: IActivity) {
    this.editForm.patchValue({
      id: activity.id,
      activityName: activity.activityName,
      appusers: activity.appusers
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const activity = this.createFromForm();
    if (activity.id !== undefined) {
      this.subscribeToSaveResponse(this.activityService.update(activity));
    } else {
      this.subscribeToSaveResponse(this.activityService.create(activity));
    }
  }

  private createFromForm(): IActivity {
    return {
      ...new Activity(),
      id: this.editForm.get(['id']).value,
      activityName: this.editForm.get(['activityName']).value,
      appusers: this.editForm.get(['appusers']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>) {
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

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
