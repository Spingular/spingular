import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

// import { IFrontpageconfig } from 'app/shared/model/frontpageconfig.model';
import { ICustomFrontpageconfig } from 'app/shared/model/customfrontpageconfig.model';
import { FrontpageconfigService } from '../entities/frontpageconfig/frontpageconfig.service';
import { ITopic } from 'app/shared/model/topic.model';
import { TopicService } from '../entities/topic/topic.service';
import { IConfigVariables } from 'app/shared/model/config-variables.model';
import { ConfigVariablesService } from '../entities/config-variables/config-variables.service';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account;
  authSubscription: Subscription;
  modalRef: NgbModalRef;
  frontpageconfigs: ICustomFrontpageconfig[];
  configVariables: IConfigVariables[];
  configVariable: IConfigVariables;
  topics: ITopic[];
  currentAccount: any;
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  queryCount: any;
  reverse: any;
  totalItems: number;
  currentSearch: string;
  id: number;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    private frontpageconfigService: FrontpageconfigService,
    private configVariablesService: ConfigVariablesService,
    private topicService: TopicService,
    private jhiAlertService: JhiAlertService,
    private activatedRoute: ActivatedRoute
  ) {
    this.frontpageconfigs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  ngOnInit() {
    this.accountService
      .identity()
      .toPromise()
      .then((account: Account) => {
        this.account = account;
        this.registerAuthenticationSuccess();
      });
    this.topicService.query().subscribe(
      (res: HttpResponse<ITopic[]>) => {
        this.topics = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.configVariablesService.query().subscribe(
      (res: HttpResponse<IConfigVariables[]>) => {
        this.configVariable = res.body[0];
        this.loadAll();
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  loadAll() {
    this.frontpageconfigService
      .findIncludingPosts(this.configVariable.configVarLong1)
      .subscribe(
        (res: HttpResponse<ICustomFrontpageconfig>) => this.paginateFrontpageconfigs(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  registerAuthenticationSuccess() {
    this.authSubscription = this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService
        .identity()
        .toPromise()
        .then(account => {
          this.account = account;
        });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  ngOnDestroy() {
    if (this.authSubscription) {
      this.eventManager.destroy(this.authSubscription);
    }
  }

  private paginateFrontpageconfigs(data: ICustomFrontpageconfig, headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = 1;
    this.frontpageconfigs.push(data);
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
