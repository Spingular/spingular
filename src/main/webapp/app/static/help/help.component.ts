import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-help',
  templateUrl: './help.component.html'
})
export class HelpComponent implements OnInit {
  currentAccount: any;

  constructor(protected accountService: AccountService) {}

  ngOnInit() {
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
  }
}
