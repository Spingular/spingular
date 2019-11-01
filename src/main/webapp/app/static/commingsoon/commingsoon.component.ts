import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-commingsoon',
  templateUrl: './commingsoon.component.html'
})
export class CommingsoonComponent implements OnInit {
  currentAccount: any;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
  }
}
