import { Component } from '@angular/core';
import { RendezvousService } from '../services';
import { RendezVous } from '../rendezvous';

@Component({
  selector: 'app-root',
  templateUrl: 'list.component.html',
  styleUrls: ['list.component.css']
})
export class ListComponent {
  
      title = 'list rendez-vous-angular';

      constructor(private service: RendezvousService) {}
      
      minDate = new Date();
    
      rendezvousList: RendezVous[] = [];
    
      ngOnInit() {
        this.loadRendezvous();
      }
    
      loadRendezvous() {
        this.service.getListRendezvous().subscribe({
          next: (response) => {
            this.rendezvousList = response;
          }
        });
      }
    
      selectRendezvous(rdv: RendezVous) {
        this.service.selectRendezvous(rdv).subscribe(() => {
          this.loadRendezvous();
        });
      } 
    
      unSelectRendezvous(rdv: RendezVous) {
        this.service.unSelectRendezvous(rdv).subscribe(() => {
          this.loadRendezvous();
        });
      } 
    

}