import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { LoginService } from 'app/core/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { JhiAlertService } from 'ng-jhipster';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from '../.././../app/entities/notification/notification.service';
import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from '../.././../app/entities/message/message.service';
import { ICmessage } from 'app/shared/model/cmessage.model';
import { CmessageService } from '../.././../app/entities/cmessage/cmessage.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from '../.././../app/entities/community/community.service';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
  inProduction: boolean;
  isNavbarCollapsed: boolean;
  languages: any[];
  swaggerEnabled: boolean;
  modalRef: NgbModalRef;

  version: string;
  currentAccount: any;
  loginName: string;

  numberOfNotifications: number;
  numberOfMessages: number;
  numberOfCmessages: number;

  notifications: INotification[];
  messages: IMessage[];
  cmessages: ICmessage[];
  communities: ICommunity[];

  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private languageHelper: JhiLanguageHelper,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router,
    private jhiAlertService: JhiAlertService,
    private notificationService: NotificationService,
    private communityService: CommunityService,
    private messageService: MessageService,
    private cmessageService: CmessageService
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
    this.isNavbarCollapsed = true;
  }

  ngOnInit() {
    this.languages = this.languageHelper.getAll();

    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
    this.accountService.getAuthenticationState().subscribe(state => {
      if (state) {
        this.loginService.editLoginStatus(true);
      }
    });
    this.loginService.loginCast.subscribe(status => {
      if (status) {
        this.loginData();
      }
    });
  }

  loginData() {
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
      this.loginName = this.currentAccount.login;
      this.mynotifications().subscribe(
        (res: HttpResponse<INotification[]>) => {
          // this.numberOfNotifications = res.body.length;
          this.notifications = res.body;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.mymessages().subscribe(
        (res: HttpResponse<IMessage[]>) => {
          this.numberOfMessages = res.body.length;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.myMessagesCommunities().subscribe(
        (res2: HttpResponse<ICommunity[]>) => {
          this.communities = res2.body;
          this.communitiesMessages().subscribe(
            (res3: HttpResponse<ICmessage[]>) => {
              this.numberOfCmessages = res3.body.length;
            },
            (res3: HttpErrorResponse) => this.onError(res3.message)
          );
        },
        (res2: HttpErrorResponse) => this.onError(res2.message)
      );
    });
  }

  private mynotifications() {
    const query = {};
    if (this.currentAccount.id != null) {
      query['userId.equals'] = this.currentAccount.id;
      query['isDelivered.equals'] = 'false';
    }
    return this.notificationService.query(query);
  }

  private mymessages() {
    const query = {};
    if (this.currentAccount.id != null) {
      query['receiverId.equals'] = this.currentAccount.id;
      query['isDelivered.equals'] = 'false';
    }
    return this.messageService.query(query);
  }

  private myMessagesCommunities() {
    const query = {};
    if (this.currentAccount.id != null) {
      query['userId.equals'] = this.currentAccount.id;
    }
    return this.communityService.query(query);
  }

  private communitiesMessages() {
    const query = {};
    if (this.communities != null) {
      const arrayCommmunities = [];
      this.communities.forEach(community => {
        arrayCommmunities.push(community.id);
      });
      query['creceiverId.in'] = arrayCommmunities;
      query['isDelivered.equals'] = 'false';
    }
    return this.cmessageService.query(query);
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  changeLanguage(languageKey: string) {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  logout() {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl() {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
  }
}
