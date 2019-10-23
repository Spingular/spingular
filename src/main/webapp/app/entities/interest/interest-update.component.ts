import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { IInterest, Interest } from 'app/shared/model/interest.model';
import { InterestService } from './interest.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-interest-update',
  templateUrl: './interest-update.component.html'
})
export class InterestUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  interest: IInterest;
  interests: IInterest[];

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
    interestName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    appusers: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected interestService: InterestService,
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

  // ngOnInit() {
  //   this.isSaving = false;
  //   this.activatedRoute.data.subscribe(({ interest }) => {
  //     this.updateForm(interest);
  //   });
  //   this.appuserService
  //     .query()
  //     .pipe(
  //       filter((mayBeOk: HttpResponse<IAppuser[]>) => mayBeOk.ok),
  //       map((response: HttpResponse<IAppuser[]>) => response.body)
  //     )
  //     .subscribe((res: IAppuser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  // }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ interest }) => {
      this.updateForm(interest);
      this.interest = interest;
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
          this.myUserInterests(this.owner);
          // this.loggedUser = res.body[0];
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private myUserInterests(owner) {
    const query = {};
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.owner;
    }
    this.interestService.query(query).subscribe(
      (res: HttpResponse<IInterest[]>) => {
        this.interests = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  loadAll() {
    if (this.currentSearch) {
      this.interestService
        .query({
          page: this.page - 1,
          'interestName.contains': this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IInterest[]>) => this.paginateInterests(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.interestService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IInterest[]>) => this.paginateInterests(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  addExistingProfileInterest(interestId) {
    this.isSaving = true;
    if (interestId !== undefined) {
      const query = {};
      query['id.equals'] = interestId;
      this.interestService.query(query).subscribe(
        (res: HttpResponse<IInterest[]>) => {
          this.interests = res.body;
          const query2 = {};
          if (this.valueParamUprofileId != null) {
            query2['id.equals'] = this.valueParamUprofileId;
          }
          this.appuserService.query(query2).subscribe(
            (res2: HttpResponse<IAppuser[]>) => {
              this.interests[0].appusers.push(res2.body[0]);
              this.subscribeToSaveResponse(this.interestService.update(this.interests[0]));
            },
            (res2: HttpErrorResponse) => this.onError(res2.message)
          );
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
    this.router.navigate(['/interest/new'], {
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

  search(query) {
    this.isCreateDisabled = true;
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/interest/new',
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
      '/interest/new',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  private paginateInterests(data: IInterest[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.interests = data;
    if (this.totalItems === 0) {
      this.interest.interestName = this.currentSearch;
    }
  }

  updateForm(interest: IInterest) {
    this.editForm.patchValue({
      id: interest.id,
      interestName: interest.interestName,
      appusers: interest.appusers
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const interest = this.createFromForm();
    if (interest.id !== undefined) {
      this.subscribeToSaveResponse(this.interestService.update(interest));
    } else {
      this.subscribeToSaveResponse(this.interestService.create(interest));
    }
  }

  private createFromForm(): IInterest {
    return {
      ...new Interest(),
      id: this.editForm.get(['id']).value,
      interestName: this.editForm.get(['interestName']).value,
      appusers: this.editForm.get(['appusers']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterest>>) {
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
