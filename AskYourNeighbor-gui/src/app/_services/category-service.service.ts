import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Unit} from "../_models/unit";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {map, skipWhile} from "rxjs/operators";
import {Category} from "../_models/category";

@Injectable({
  providedIn: 'root'
})
export class CategoryServiceService {

  private categories: BehaviorSubject<Category[]> = new BehaviorSubject<Category[]>([]);
  private categories$: Observable<Category[]> = this.categories.asObservable();
  private pending = false;

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    if(this.categories.value.length === 0 && !this.pending) {
      this.pending = true;
      this.http.get<Category[]>(`${environment.apiUrl}/categories`).subscribe(x => {
        this.categories.next(x);
        this.pending = false;
      });
    }

    return this.categories$;
  }

  getCategory(idCategory: number): Observable<Category> {
    this.getCategories();
    return this.categories$.pipe(skipWhile(x => x.length === 0)).pipe(map(x => {
      return x.find(s => s.id === idCategory);
    }));
  }
}
