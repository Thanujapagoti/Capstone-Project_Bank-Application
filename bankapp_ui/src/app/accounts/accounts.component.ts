import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent {
  accounts = [
    { number: '123456', type: 'Savings', balance: 5000 },
    { number: '789012', type: 'Current', balance: 12000 }
  ];
}
