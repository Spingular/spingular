import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ICceleb, Cceleb } from 'app/shared/model/cceleb.model';
import { CcelebService } from './cceleb.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community/community.service';

import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

// import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Component({
  selector: 'jhi-cceleb-update',
  templateUrl: './cceleb-update.component.html'
})
export class CcelebUpdateComponent implements OnInit {
  isSaving: boolean;

  communities: ICommunity[];
  arrayCommmunities = [];

  ccelebs: ICceleb[];
  cceleb: ICceleb;
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
    celebName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    communities: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ccelebService: CcelebService,
    protected communityService: CommunityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected parseLinks: JhiParseLinks,
    protected appuserService: AppuserService,
    protected accountService: AccountService,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params.communityIdEquals != null) {
        this.nameParamCommunityId = 'community.id ';
        this.valueParamCommunityId = params.communityIdEquals;
      }
    });
  }

  ngOnInit() {
    this.isSaving = false;
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
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.activatedRoute.data.subscribe(({ cceleb }) => {
      this.cceleb = cceleb;
      this.updateForm(cceleb);
    });
    if (this.valueParamCommunityId != null) {
      const query = {};
      query['id.equals'] = this.valueParamCommunityId;
      this.communityService.query(query).subscribe(
        (res: HttpResponse<ICommunity[]>) => {
          this.communities = res.body;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    } else {
      this.communityService.query().subscribe(
        (res: HttpResponse<ICommunity[]>) => {
          this.communities = res.body;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    }
  }

  loadAll() {
    if (this.currentSearch) {
      this.ccelebService
        .query({
          page: this.page - 1,
          'celebName.contains': this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ICceleb[]>) => this.paginateCcelebs(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.ccelebService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICceleb[]>) => this.paginateCcelebs(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  addExistingCommunityCceleb(ccelebId) {
    this.isSaving = true;
    if (ccelebId !== undefined) {
      const query = {};
      query['id.equals'] = ccelebId;
      this.ccelebService.query(query).subscribe(
        (res: HttpResponse<ICceleb[]>) => {
          this.ccelebs = res.body;
          const query2 = {};
          if (this.valueParamCommunityId != null) {
            query2['id.equals'] = this.valueParamCommunityId;
          }
          this.communityService.query(query2).subscribe(
            (res2: HttpResponse<ICommunity[]>) => {
              this.ccelebs[0].communities.push(res2.body[0]);
              this.subscribeToSaveResponse(this.ccelebService.update(this.ccelebs[0]));
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
    this.router.navigate(['/cceleb/new'], {
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
      '/cceleb/new',
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
      '/cceleb/new',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  private paginateCcelebs(data: ICceleb[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.ccelebs = data;
    if (this.totalItems === 0) {
      this.cceleb.celebName = this.currentSearch;
    }
  }

  updateForm(cceleb: ICceleb) {
    this.editForm.patchValue({
      id: cceleb.id,
      celebName: cceleb.celebName,
      communities: cceleb.communities
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cceleb = this.createFromForm();
    if (cceleb.id !== undefined) {
      this.subscribeToSaveResponse(this.ccelebService.update(cceleb));
    } else {
      this.subscribeToSaveResponse(this.ccelebService.create(cceleb));
    }
  }

  private createFromForm(): ICceleb {
    return {
      ...new Cceleb(),
      id: this.editForm.get(['id']).value,
      celebName: this.editForm.get(['celebName']).value,
      communities: this.editForm.get(['communities']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICceleb>>) {
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

  trackId(index: number, item: ICceleb) {
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
