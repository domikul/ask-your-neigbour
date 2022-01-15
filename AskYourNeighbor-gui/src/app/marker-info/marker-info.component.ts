import {Component, Input, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {Form} from "../_models/form";
import {UnitService} from "../_services/unit.service";
import {Unit} from "../_models/unit";

@Component({
  selector: 'app-marker-info',
  templateUrl: './marker-info.component.html',
  styleUrls: ['./marker-info.component.css']
})
export class MarkerInfoComponent implements OnInit {

  @Input() receivedStats: Subject<Form>;
  form: Form;
  units: Unit[];

  constructor(private unitService: UnitService) { }

  ngOnInit(): void {

    this.receivedStats.subscribe( formInfo => {
      this.form = formInfo;
    })

    this.unitService.getUnits().subscribe(x => {
      this.units = x;
    })

  }
}
