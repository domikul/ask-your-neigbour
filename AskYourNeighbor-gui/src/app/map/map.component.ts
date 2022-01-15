import {Component, OnInit} from '@angular/core';
import {Subject} from 'rxjs';
import {UnitService} from "../_services/unit.service";
import {CategoryServiceService} from "../_services/category-service.service";
import {FormServiceService} from "../_services/form-service.service";
import {Form} from "../_models/form";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  zoom = 12;
  center: google.maps.LatLngLiteral;
  geocoder = new google.maps.Geocoder();
  markers: Array<any> = new Array<any>();
  forms: Form[];
  // locations: Array<any> = new Array<any>();
  markersFormsMap: Map<any, number> = new Map<any, number>();
  statsToSend = new Subject<Form>();

  constructor(public unitService: UnitService,
              public categoryService: CategoryServiceService,
              public formService: FormServiceService) {
  }

  ngOnInit(): void {

    this.formService.getFormsToReview().subscribe(x => {
      this.forms = x;
      console.log(x);
      for (const val of x) {
        let s = val.street.concat(" ").concat(val.number).concat(", ").concat(val.city);
        // this.locations.push(s);
        this.addMarker(s, this.markers, val.id, this.markersFormsMap);
      }
    })

    navigator.geolocation.getCurrentPosition((position) => {
      this.center = {
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      };
    });

  }

  addMarker(location, markers, valId, markersFormsMap) {
    this.geocoder.geocode({'address': location}, function (results, status) {
      let latLng = {lat: results[0].geometry.location.lat(), lng: results[0].geometry.location.lng()};
      if (status == 'OK') {
        let marker = new google.maps.Marker({
          position: {
            lat: latLng.lat,
            lng: latLng.lng,
          },
          label: {
            color: 'red',
            text: 'Marker label ' + (markers.length + 1),
          },
          title: 'Marker title ' + (markers.length + 1),
        });
        markers.push(marker);
        console.log('latLng: ' + latLng.lat + ' ' + latLng.lng);
        console.log(valId);
        markersFormsMap.set(latLng.lat.toString().concat(latLng.lng.toString()), valId);
        console.log(markersFormsMap);
      }
    });
  }

  onMarkerClick($event: google.maps.MapMouseEvent) {
    console.log($event.latLng.lat());
    console.log($event.latLng.lng());
    console.log(this.markersFormsMap);
    this.formService.getFormById(this.markersFormsMap.get($event.latLng.lat().toString().concat($event.latLng.lng().toString()))).subscribe(x => {
      this.statsToSend.next(x);
      console.log(x);
    });
  }
}
