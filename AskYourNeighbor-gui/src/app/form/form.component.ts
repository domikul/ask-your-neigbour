import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSelect} from "@angular/material/select";
import {UnitService} from "../_services/unit.service";
import {CategoryServiceService} from "../_services/category-service.service";
import {FormServiceService} from "../_services/form-service.service";
import {Form} from "../_models/form";
import {take, takeUntil} from "rxjs/operators";
import {Category} from "../_models/category";
import {Unit} from "../_models/unit";
import {Subject} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {NgForm} from "@angular/forms";
import {InfoDialogComponent} from "../info-dialog/info-dialog.component";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  form: Form = new Form();
  units: Unit[];
  categories: Category[];
  unsubscribe$ = new Subject();

  @ViewChild('categorySelect') categorySelect: MatSelect;
  @ViewChild('unitSelect') unitSelect: MatSelect;

  constructor(public unitService: UnitService,
              public categoryService: CategoryServiceService,
              public formService: FormServiceService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.categoryService.getCategories().pipe(takeUntil(this.unsubscribe$)).subscribe(x => {
      this.categories = x;
    });

    this.unitService.getUnits().pipe(takeUntil(this.unsubscribe$)).subscribe(x => {
      this.units = x;
    });
  }

  submit() {
    this.formService.createNewForm(this.form).pipe(take(1)).subscribe(x => {
    });
  }

  openInfoDialog() {
    const dialogRef = this.dialog.open(InfoDialogComponent,{
      data:{
        message: 'Dziękujemy za przesłanie zgłoszenia.',
        buttonText: {
          cancel: 'OK'
        }
      },
    });
  }

}
