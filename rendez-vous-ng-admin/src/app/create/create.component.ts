import { Component } from '@angular/core';
import { RendezvousService } from '../services';
import { RendezVous } from '../rendezvous';

@Component({
  selector: 'app-root',
  templateUrl: 'create.component.html',
  styleUrls: ['create.component.css']
})
export class CreateComponent {
  
  title = 'create rendez-vous-angular';

  rendezvousList: RendezVous[] = [];

  date: string = '';
  heure: string = '';
  today: string = new Date().toISOString().split('T')[0];

  constructor(private rendezvousService: RendezvousService) {}

  ngOnInit() {
    this.getRendezvous();
  }

  onSubmit() {
    const rdv: RendezVous = {
      date: this.date,
      heure: this.heure,
      reservedId: '' // Vous pouvez définir une valeur par défaut ou la laisser vide si ce n'est pas nécessaire
    };

    this.rendezvousService.addRendezvous(rdv).subscribe({
      next: (response) => {
        this.getRendezvous();
      },
      error: (err) => {
        console.error('Erreur lors de la création du RendezVous', err);
      }
    });
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