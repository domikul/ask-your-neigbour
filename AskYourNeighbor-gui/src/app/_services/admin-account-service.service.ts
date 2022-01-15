import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AdminAccount} from "../_models/admin-account";
import {environment} from "../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
import {shareReplay, take } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminAccountServiceService {

  private users: BehaviorSubject<AdminAccount[]> = new BehaviorSubject<AdminAccount[]>([]);
  private users$: Observable<AdminAccount[]> = this.users.asObservable();

  constructor(private http: HttpClient) { }

  registerUser(user: AdminAccount): Observable<AdminAccount> {
    const user$ = this.http.post<AdminAccount>(`${environment.apiUrl}/account`, user).pipe(shareReplay());
    user$.pipe(take(1)).subscribe(x => {
      const users = this.users.value;
      users.unshift(x);
      this.users.next(users);
    });

    return user$;
  }

}
