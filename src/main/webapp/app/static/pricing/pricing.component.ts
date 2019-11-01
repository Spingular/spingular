import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-pricing',
  templateUrl: './pricing.component.html'
})
export class PricingComponent implements OnInit {
  currentAccount: any;

  constructor(protected accountService: AccountService) {}

  ngOnInit() {
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
  }
}
