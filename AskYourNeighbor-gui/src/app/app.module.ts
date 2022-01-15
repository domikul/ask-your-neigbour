import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './map/map.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {FlexLayoutModule, FlexModule} from "@angular/flex-layout";
import {RouterModule} from "@angular/router";
import {GoogleMapsModule} from '@angular/google-maps';
import {MatIconModule} from "@angular/material/icon";
import {MatTabsModule} from "@angular/material/tabs";
import { FormComponent } from './form/form.component';
import {MatButtonModule} from "@angular/material/button";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MarkerInfoComponent } from './marker-info/marker-info.component';
import {MatCardModule} from "@angular/material/card";
import { InfoDialogComponent } from './info-dialog/info-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import { RegisterComponent } from './register/register.component';
import {HttpErrorInterceptor} from "./_helpers/error-interceptor";
import { MatSnackBarModule } from '@angular/material/snack-bar';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    FormComponent,
    MarkerInfoComponent,
    InfoDialogComponent,
    RegisterComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        MatToolbarModule,
        BrowserAnimationsModule,
        FlexModule,
        FlexLayoutModule,
        GoogleMapsModule,
        MatSnackBarModule,
        RouterModule.forRoot([
            {path: '', redirectTo: '/mapa', pathMatch: 'full'},
            {path: 'mapa', component: MapComponent},
            {path: 'formularz', component: FormComponent},
            {path: 'rejestracja', component: RegisterComponent},
        ]),
        MatIconModule,
        MatTabsModule,
        MatButtonModule,
        MatFormFieldModule,
        FormsModule,
        MatInputModule,
        MatSelectModule,
        HttpClientModule,
        MatCardModule,
        MatDialogModule
    ],
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}},
    {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi:true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
