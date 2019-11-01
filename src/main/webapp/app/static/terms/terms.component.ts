import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-terms',
  templateUrl: './terms.component.html'
})
export class TermsComponent implements OnInit {
  currentAccount: any;

  constructor(protected accountService: AccountService) {}

  ngOnInit() {
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
  }
}
