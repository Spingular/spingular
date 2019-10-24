import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ICinterest, Cinterest } from 'app/shared/model/cinterest.model';
import { CinterestService } from './cinterest.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

// import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-cinterest-update',
  templateUrl: './cinterest-update.component.html'
})
export class CinterestUpdateComponent implements OnInit {
  isSaving: boolean;

  communities: ICommunity[];
  arrayCommmunities = [];

  cinterests: ICinterest[];
  cinterest: ICinterest;
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
    interestName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    communities: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cinterestService: CinterestService,
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
    this.activatedRoute.data.subscribe(({ cinterest }) => {
      this.cinterest = cinterest;
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
      this.cinterestService
        .query({
          page: this.page - 1,
          'interestName.contains': this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ICinterest[]>) => this.paginateCinterests(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.cinterestService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICinterest[]>) => this.paginateCinterests(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  addExistingProfileCinterest(cinterestId) {
    this.isSaving = true;
    if (cinterestId !== undefined) {
      const query = {};
      query['id.equals'] = cinterestId;
      this.cinterestService.query(query).subscribe(
        (res: HttpResponse<ICinterest[]>) => {
          this.cinterests = res.body;
          const query2 = {};
          if (this.valueParamCommunityId != null) {
            query2['id.equals'] = this.valueParamCommunityId;
          }
          this.communityService.query(query2).subscribe(
            (res2: HttpResponse<ICommunity[]>) => {
              this.cinterests[0].communities.push(res2.body[0]);
              this.subscribeToSaveResponse(this.cinterestService.update(this.cinterests[0]));
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
    this.router.navigate(['/cinterest/new'], {
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
      '/cinterest/new',
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
      '/cinterest/new',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  private paginateCinterests(data: ICinterest[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.cinterests = data;
    if (this.totalItems === 0) {
      this.cinterest.interestName = this.currentSearch;
    }
  }

  updateForm(cinterest: ICinterest) {
    this.editForm.patchValue({
      id: cinterest.id,
      interestName: cinterest.interestName,
      communities: cinterest.communities
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cinterest = this.createFromForm();
    if (cinterest.id !== undefined) {
      this.subscribeToSaveResponse(this.cinterestService.update(cinterest));
    } else {
      this.subscribeToSaveResponse(this.cinterestService.create(cinterest));
    }
  }

  private createFromForm(): ICinterest {
    return {
      ...new Cinterest(),
      id: this.editForm.get(['id']).value,
      interestName: this.editForm.get(['interestName']).value,
      communities: this.editForm.get(['communities']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICinterest>>) {
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

  trackId(index: number, item: ICinterest) {
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
