import { Injectable } from '@angular/core';
import { flatMap } from 'rxjs/operators';
import { AccountService } from 'app/core/auth/account.service';
import { AuthServerProvider } from 'app/core/auth/auth-session.service';

import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({ providedIn: 'root' })
export class LoginService {
  private loginStatus = new BehaviorSubject<boolean>(false);
  public loginCast = this.loginStatus.asObservable();

  constructor(private accountService: AccountService, private authServerProvider: AuthServerProvider) {}

  login(credentials) {
    return this.authServerProvider.login(credentials).pipe(flatMap(() => this.accountService.identity(true)));
  }

  editLoginStatus(status) {
    this.loginStatus.next(status);
  }

  logout() {
    this.authServerProvider.logout().subscribe(null, null, () => this.accountService.authenticate(null));
  }
}
