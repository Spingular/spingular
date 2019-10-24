import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ICactivity, Cactivity } from 'app/shared/model/cactivity.model';
import { CactivityService } from './cactivity.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

// import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-cactivity-update',
  templateUrl: './cactivity-update.component.html'
})
export class CactivityUpdateComponent implements OnInit {
  isSaving: boolean;

  communities: ICommunity[];
  arrayCommmunities = [];

  cactivities: ICactivity[];
  cactivity: ICactivity;
  appusers: IAppuser[];
  appuser: IAppuser;
  isCreateDisabled = false;

  nameParamCommunityId: any;
  valueParamCommunityId: any;

  owner: any;
  isAdmin: boolean;

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

  editForm = this.fb.group({
    id: [],
    activityName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    communities: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cactivityService: CactivityService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected parseLinks: JhiParseLinks,
    protected appuserService: AppuserService,
    protected accountService: AccountService,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cactivity }) => {
      this.cactivity = cactivity;
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
          const query2 = {};
          if (this.currentAccount.id != null) {
            query2['appuserId.equals'] = this.owner;
          }
          this.communityService.query(query2).subscribe(
            (res2: HttpResponse<ICommunity[]>) => {
              this.communities = res2.body;
            },
            (res2: HttpErrorResponse) => this.onError(res2.message)
          );
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  loadAll() {
    if (this.currentSearch) {
      this.cactivityService
        .query({
          page: this.page - 1,
          'activityName.contains': this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ICactivity[]>) => this.paginateCactivitys(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.cactivityService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICactivity[]>) => this.paginateCactivitys(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  addExistingProfileCactivity(cactivityId) {
    this.isSaving = true;
    if (cactivityId !== undefined) {
      const query = {};
      query['id.equals'] = cactivityId;
      this.cactivityService.query(query).subscribe(
        (res: HttpResponse<ICactivity[]>) => {
          this.cactivities = res.body;
          const query2 = {};
          if (this.valueParamCommunityId != null) {
            query2['id.equals'] = this.valueParamCommunityId;
          }
          this.communityService.query(query2).subscribe(
            (res2: HttpResponse<ICommunity[]>) => {
              this.cactivities[0].communities.push(res2.body[0]);
              this.subscribeToSaveResponse(this.cactivityService.update(this.cactivities[0]));
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
    this.router.navigate(['/cactivity/new'], {
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
      '/cactivity/new',
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
      '/cactivity/new',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  private paginateCactivitys(data: ICactivity[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.cactivities = data;
    if (this.totalItems === 0) {
      this.cactivity.activityName = this.currentSearch;
    }
  }

  updateForm(cactivity: ICactivity) {
    this.editForm.patchValue({
      id: cactivity.id,
      activityName: cactivity.activityName,
      communities: cactivity.communities
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cactivity = this.createFromForm();
    if (cactivity.id !== undefined) {
      this.subscribeToSaveResponse(this.cactivityService.update(cactivity));
    } else {
      this.subscribeToSaveResponse(this.cactivityService.create(cactivity));
    }
  }

  private createFromForm(): ICactivity {
    return {
      ...new Cactivity(),
      id: this.editForm.get(['id']).value,
      activityName: this.editForm.get(['activityName']).value,
      communities: this.editForm.get(['communities']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICactivity>>) {
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

  trackCommunityById(index: number, item: ICommunity) {
    return item.id;
  }

  trackId(index: number, item: ICactivity) {
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
