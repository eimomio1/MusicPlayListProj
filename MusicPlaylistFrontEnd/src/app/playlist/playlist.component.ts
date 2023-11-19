import { Component, OnInit } from '@angular/core';
import { PlaylistService } from './playlist.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css']
})
export class PlaylistComponent implements OnInit {
  nameOfPlaylist: string = '';
  userId: string = '';
  playlistId: string = ''; 
  updatedPlaylistName: string = ''; // new property
  updatedPlaylistDescription: string = ''; // new property
  playlists: any[] = [];
  selectedPlaylistId: string = ''; // new property
  selectedDeletePlaylistId: string = ''; // new property




  constructor(
    private playlistService: PlaylistService,
    private route: ActivatedRoute,
    private router: Router // Inject the Router service
  ) {}

  ngOnInit(): void {
    // Extract userId from the URL
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];

      this.loadPlaylists();
    });
  }

  createPlaylist(): void {
    if (this.nameOfPlaylist && this.userId) {
      // Send the playlist name as a string
      this.playlistService.createPlaylist(this.userId, this.nameOfPlaylist).subscribe(
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

  updatePlaylist(): void {
    if (this.userId && this.playlistId && (this.updatedPlaylistName || this.updatedPlaylistDescription)) {
      const updatedPlaylist = {
        name: this.updatedPlaylistName,
        description: this.updatedPlaylistDescription
      };

      this.playlistService.updatePlaylist(this.userId, this.playlistId, updatedPlaylist).subscribe(
        response => {
          console.log('Playlist updated successfully:', response);
          // Handle success, e.g., show a success message to the user
        },
        error => {
          console.error('Failed to update playlist:', error);
          // Handle error, e.g., show an error message to the user
        }
      );
    } else {
      // Handle invalid input or missing userId/playlistId/updatedPlaylistName/updatedPlaylistDescription
      // e.g., show a validation message
    }
  }
 
// Update the URL when a playlist is selected
onPlaylistSelected(): void {
  // Update the URL with the selected playlistId
  this.router.navigate([], {
    relativeTo: this.route,
    queryParams: { playlistId: this.selectedPlaylistId },
    queryParamsHandling: 'merge'
  });

  // Set the playlistId property
  this.playlistId = this.selectedPlaylistId;
}




private loadPlaylists(): void {
  this.playlistService.getPlaylists(this.userId).subscribe(
    playlists => {
      // Extract only the names of the playlists
      this.playlists = playlists.map(playlist => ({ playlistName: playlist.name, spotifyId: playlist.id }));
      
      // Extract playlistId from the URL
      this.route.queryParams.subscribe(params => {
        this.selectedPlaylistId = params['playlistId'] || ''; // Default to an empty string
      });
    },
    error => {
      console.error('Failed to load playlists:', error);
      // Handle error, e.g., show an error message to the user
    }
  );
}

onDeletePlaylistSelected(): void {
  // Update the URL with the selected playlistId for deletion
  this.router.navigate([], {
    relativeTo: this.route,
    queryParams: { playlistId: this.selectedDeletePlaylistId },
    queryParamsHandling: 'merge'
  });

  this.playlistId = this.selectedDeletePlaylistId;
}

deletePlaylist(): void {
  if (this.userId && this.selectedDeletePlaylistId) {
    this.playlistService.deletePlaylist(this.userId, this.selectedDeletePlaylistId).subscribe(
      response => {
        console.log('Playlist deleted successfully:', response);
        // Handle success, e.g., show a success message to the user
      },
      error => {
        console.error('Failed to delete playlist:', error);
        // Handle error, e.g., show an error message to the user
      }
    );
  } else {
    // Handle invalid input or missing userId/selectedDeletePlaylistId
    // e.g., show a validation message
  }
}




}
