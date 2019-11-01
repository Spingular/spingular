import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-credits',
  templateUrl: './credits.component.html'
})
export class CreditsComponent implements OnInit {
  currentAccount: any;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
  }
}
