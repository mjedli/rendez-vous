import { Component } from '@angular/core';
import { RendezVous } from '../rendezvous';
import { RendezvousService } from '../services';

@Component({
  selector: 'app-root',
  templateUrl: 'list.component.html',
  styleUrls: ['list.component.css']
})
export class ListComponent {
  
    title = 'list rendez-vous-angular';
    rendezvousList: RendezVous[] = [];

    constructor(private rendezvousService: RendezvousService) {}
  
    ngOnInit() {
      this.getRendezvous();
    }
      
    getRendezvous() {
      this.rendezvousService.getRendezvous().subscribe({
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
        this.getRendezvous();
      },
      error: (err) => {
      console.error('Erreur lors de la suppression', err);
      }
    });
  }
}