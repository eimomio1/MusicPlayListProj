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
    const url = `${this.baseUrl}/api/create-playlist/users/${username}/playlists`;

    return this.http.post(url, playlistData, { responseType: 'text' });
  }
  
  updatePlaylist(userId: string, playlistId: string, playlistData: any): Observable<any> {
    const url = `${this.baseUrl}/api/user/playlist/${playlistId}?userId=${userId}`;

    return this.http.put(url, playlistData, { responseType: 'text' });
  }
 

  getPlaylists(userId: string): Observable<any[]> {
    const url = `${this.baseUrl}/api/users/${userId}/playlists`;
    return this.http.get<any[]>(url);
  }

  
  deletePlaylist(userId: string, playlistId: string): Observable<any> {
    const url = `${this.baseUrl}/api/delete-playlist/users/${playlistId}?userId=${userId}`;
  
    return this.http.delete(url, { responseType: 'text' });
  }
  
}
