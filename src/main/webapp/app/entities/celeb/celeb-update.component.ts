import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
// import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ICeleb, Celeb } from 'app/shared/model/celeb.model';
import { CelebService } from './celeb.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-celeb-update',
  templateUrl: './celeb-update.component.html'
})
export class CelebUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppuser[];

  celeb: ICeleb;
  celebs: ICeleb[];

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
    celebName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(40)]],
    appusers: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected celebService: CelebService,
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
    this.activatedRoute.data.subscribe(({ celeb }) => {
      this.updateForm(celeb);
      this.celeb = celeb;
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
          this.myUserCelebs(this.owner);
          // this.loggedUser = res.body[0];
        });
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private myUserCelebs(owner) {
    const query = {};
    if (this.currentAccount.id != null) {
      query['appuserId.equals'] = this.owner;
    }
    this.celebService.query(query).subscribe(
      (res: HttpResponse<ICeleb[]>) => {
        this.celebs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  loadAll() {
    if (this.currentSearch) {
      this.celebService
        .query({
          page: this.page - 1,
          'celebName.contains': this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ICeleb[]>) => this.paginateCelebs(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.celebService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICeleb[]>) => this.paginateCelebs(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  addExistingProfileCeleb(celebId) {
    this.isSaving = true;
    if (celebId !== undefined) {
      const query = {};
      query['id.equals'] = celebId;
      this.celebService.query(query).subscribe(
        (res: HttpResponse<ICeleb[]>) => {
          this.celebs = res.body;
          const query2 = {};
          if (this.valueParamUprofileId != null) {
            query2['id.equals'] = this.valueParamUprofileId;
          }
          this.appuserService.query(query2).subscribe(
            (res2: HttpResponse<IAppuser[]>) => {
              this.celebs[0].appusers.push(res2.body[0]);
              this.subscribeToSaveResponse(this.celebService.update(this.celebs[0]));
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
    this.router.navigate(['/celeb/new'], {
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
      '/celeb/new',
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
      '/celeb/new',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  private paginateCelebs(data: ICeleb[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.celebs = data;
    if (this.totalItems === 0) {
      this.celeb.celebName = this.currentSearch;
    }
  }

  updateForm(celeb: ICeleb) {
    this.editForm.patchValue({
      id: celeb.id,
      celebName: celeb.celebName,
      appusers: celeb.appusers
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const celeb = this.createFromForm();
    if (celeb.id !== undefined) {
      this.subscribeToSaveResponse(this.celebService.update(celeb));
    } else {
      this.subscribeToSaveResponse(this.celebService.create(celeb));
    }
  }

  private createFromForm(): ICeleb {
    return {
      ...new Celeb(),
      id: this.editForm.get(['id']).value,
      celebName: this.editForm.get(['celebName']).value,
      appusers: this.editForm.get(['appusers']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICeleb>>) {
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
