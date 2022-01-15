import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {Unit} from "../_models/unit";
import {environment} from "../../environments/environment";
import {map, skipWhile} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UnitService {

  private units: BehaviorSubject<Unit[]> = new BehaviorSubject<Unit[]>([]);
  private units$: Observable<Unit[]> = this.units.asObservable();

  constructor(private http: HttpClient) { }

  getUnits(): Observable<Unit[]> {
    this.http.get<Unit[]>(`${environment.apiUrl}/units`).subscribe(x => {
      this.units.next(x);
    });
    return this.units$;
  }

  getUnit(idUnit: number): Observable<Unit> {
    this.getUnits();
    return this.units$.pipe(skipWhile(x => x.length === 0)).pipe(map(x => {
      return x.find(s => s.id === idUnit);
    }));
  }

}
