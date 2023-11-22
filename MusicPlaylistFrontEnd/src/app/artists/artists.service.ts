import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArtistsService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  searchArtist(userId: string, query: string): Observable<any[]> {
    const url = `${this.baseUrl}/api/search?userId=${userId}&q=${query}&type=artist`;
    return this.http.get<any[]>(url);
  }

  getAlbumsByArtistId(artistId: string, userId: string): Observable<any[]> {
    const url = `${this.baseUrl}/api/artists/${artistId}/albums?userId=${userId}`;
    return this.http.get<any[]>(url);
  }

  getAlbumTracks(albumId: string, userId: string): Observable<any[]> {
    const url = `${this.baseUrl}/api/albums/${albumId}/tracks?userId=${userId}`;
    return this.http.get<any[]>(url);
  }
  
}
