import { Component, OnInit } from '@angular/core';
import { take } from 'rxjs/operators';
import {AdminAccount} from "../_models/admin-account";
import {AdminAccountServiceService} from "../_services/admin-account-service.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  adminAccount: AdminAccount = new AdminAccount();

  constructor(private adminService: AdminAccountServiceService) { }

  ngOnInit(): void {
  }

  submit() {
    this.adminService.registerUser(this.adminAccount).pipe(take(1)).subscribe(x => {
    });
  }

}
