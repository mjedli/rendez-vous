import { Component } from '@angular/core';
import { RendezvousService } from '../services';
import { RendezVous } from '../rendezvous';

@Component({
  selector: 'app-root',
  templateUrl: 'depasser.component.html',
  styleUrls: ['depasser.component.css']
})
export class DepasserComponent {
  
  title = 'depasser rendez-vous-angular';
  startDate= new Date();
  minDate = new Date();

  rendezvousList: RendezVous[] = [];

  constructor(private rendezvousService: RendezvousService) {}

  ngOnInit() {
    this.getRendezvousDepasser();
  }
    
  getRendezvousDepasser() {
    this.rendezvousService.getRendezvousDepasser().subscribe({
      next: (response) => {
        this.rendezvousList = response;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des RendezVous', err);
      }
    });
  }

  deleteRendezvous(id: string): void {
    this.rendezvousService.deleteRendezvous(id).subscribe({
      next: (response) => {
        this.getRendezvousDepasser();
      },
      error: (err) => {
      console.error('Erreur lors de la suppression', err);
      }
    });
  }

}