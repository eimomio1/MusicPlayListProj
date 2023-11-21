import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArtistsService {
  private baseUrl = 'http://localhost:8080'; // Replace with your backend URL and port

  constructor(private http: HttpClient) {}


  // Updated searchSongs method to accept userId and query
searchSongs(userId: string, query: string): Observable<any[]> {
    const url = `${this.baseUrl}/api/search?userId=${userId}&q=${query}&type=artist`;
    return this.http.get<any[]>(url);
  }


  getAlbumsByArtistId(artistId: string, userId: string): Observable<any[]> {
    const url = `${this.baseUrl}/api/artists/${artistId}/albums?userId=${userId}`;
    return this.http.get<any[]>(url);
}


}
