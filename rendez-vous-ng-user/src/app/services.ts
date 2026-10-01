import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RendezVous } from './rendezvous';

@Injectable({
  providedIn: 'root'
})
export class RendezvousService {
  private apiUrl = 'http://localhost:8080/user/rendezvous/';

  constructor(private http: HttpClient) {}

  getRendezvous(date:string): Observable<RendezVous[]> {
    return this.http.get<RendezVous[]>(this.apiUrl+date);
  }

  deleteRendezvous(id: string): Observable<String> {
    return this.http.delete<String>(`${this.apiUrl}delete/${id}`);
  }
}