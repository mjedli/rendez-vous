export interface RendezVous {
  id?: string;        // optionnel car généré par MongoDB
  date: string;       // format YYYY-MM-DD
  heure: string;      // format HH:mm
  reservedId: string; // identifiant de réservation
}
