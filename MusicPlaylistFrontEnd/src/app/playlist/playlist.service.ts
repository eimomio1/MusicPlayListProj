import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {
  private baseUrl = 'http://localhost:8080'; // Replace with your backend URL and port

  constructor(private http: HttpClient) {}

  createPlaylist(username: string, playlistData: string): Observable<any> {
    const url = `${this.baseUrl}/playlist/create-playlist/users/${username}/playlists`;

    return this.http.post(url, playlistData, { responseType: 'text' });
  }
}
