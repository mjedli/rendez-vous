import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: 'select.component.html',
  styleUrls: ['select.component.css']
})
export class SelectComponent {
  
  title = 'select rendez-vous-angular';
  startDate= new Date();
  minDate = new Date();

}