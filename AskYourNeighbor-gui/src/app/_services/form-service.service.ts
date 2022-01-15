import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Form} from "../_models/form";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FormServiceService {

  constructor(private http: HttpClient) { }

  getActiveForms() {
    return this.http.get<Form[]>(`${environment.apiUrl}/forms/active`);
  }

  getFormsToReview() {
    return this.http.get<Form[]>(`${environment.apiUrl}/forms/queue`);
  }

  getFormById(idForm: number) {
    return this.http.get<Form>(`${environment.apiUrl}/forms/`+ idForm);
  }

  createNewForm(form: Form) {
    return this.http.post(`${environment.apiUrl}/forms/`, form);
  }

  deleteFormById(idForm: number) {
    return this.http.delete<any>(`${environment.apiUrl}/forms/` + idForm);
  }

  acceptForm(idForm: number) {
    return this.http.patch<Form>(`${environment.apiUrl}/forms/` + idForm, {})
  }

}
