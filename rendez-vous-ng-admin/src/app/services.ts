import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RendezVous } from './rendezvous';

@Injectable({
  providedIn: 'root'
})
export class RendezvousService {
  private apiUrl = 'http://localhost:8080/admin/rendezvous/';

  constructor(private http: HttpClient) {}

  addRendezvous(rdv: RendezVous): Observable<string> {
    return this.http.post<string>(this.apiUrl+'add', rdv);
  }

  getRendezvous(): Observable<RendezVous[]> {
    return this.http.get<RendezVous[]>(this.apiUrl+'list/venir');
  }

  getRendezvousDepasser(): Observable<RendezVous[]> {
    return this.http.get<RendezVous[]>(this.apiUrl+'list/depasser');
  }

  deleteRendezvous(id: string): Observable<String> {
    return this.http.delete<String>(`${this.apiUrl}delete/${id}`);
}
}

