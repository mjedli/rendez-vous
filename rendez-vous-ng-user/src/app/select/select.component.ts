import { Component } from '@angular/core';
import { RendezvousService } from '../services';
import { RendezVous } from '../rendezvous';

@Component({
  selector: 'app-root',
  templateUrl: 'select.component.html',
  styleUrls: ['select.component.css']
})
export class SelectComponent {

  constructor(private service: RendezvousService) {}
  
  title = 'select rendez-vous-angular';
  startDate= new Date();
  minDate = new Date();

  rendezvousList: RendezVous[] = [];

  ngOnInit() {
    this.loadRendezvous(this.startDate);
  }

  onDateChange(event: any) {
    this.loadRendezvous(event.value);
  }

  loadRendezvous(date: Date) {
    const dateStr =
    date.getFullYear() + '-' +
    String(date.getMonth() + 1).padStart(2, '0') + '-' +
    String(date.getDate()).padStart(2, '0');
     
    this.service.getRendezvous(dateStr).subscribe({
      next: (response) => {
      console.log(response);
      this.rendezvousList = response;
      }
    });
  }

  deleteRendezvous(id: string) {
    this.service.deleteRendezvous(id).subscribe(() => {
      this.rendezvousList = this.rendezvousList.filter(rdv => rdv.id !== id);
    });
  } 

}