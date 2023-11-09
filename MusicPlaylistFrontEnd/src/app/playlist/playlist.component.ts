import { Component, OnInit } from '@angular/core';
import { PlaylistService } from './playlist.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css']
})
export class PlaylistComponent implements OnInit {
  nameOfPlaylist: string = '';
  userId: string = ''; // New property to store userId

  constructor(
    private playlistService: PlaylistService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Extract userId from the URL
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
    });
  }

  createPlaylist(): void {
    if (this.nameOfPlaylist && this.userId) {
      const playlistData = {
        nameOfPlaylist: this.nameOfPlaylist
      };
  
      this.playlistService.createPlaylist(this.userId, playlistData).subscribe(
        response => {
          console.log('Playlist created successfully:', response);
          // Handle success, e.g., show a success message to the user
        },
        error => {
          console.error('Failed to create playlist:', error);
          // Handle error, e.g., show an error message to the user
        }
      );
    } else {
      // Handle invalid input or missing userId, e.g., show a validation message
    }
  }
}
