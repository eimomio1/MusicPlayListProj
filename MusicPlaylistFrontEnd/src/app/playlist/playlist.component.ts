import { Component, OnInit } from '@angular/core';
import { PlaylistService } from './playlist.service';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { Subject } from 'rxjs';

interface ApiResponse {
  albums: any;
  artists: any;
  episodes: any;
  playlists: any;
  shows: any;
  tracks: {
    href: string;
    items: any[];
    limit: number;
    next: string;
    offset: number;
    // Add any other properties if needed
  };
}


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
  

  searchQuery: string = '';
  searchResults: any[] = [];
  selectedSongId: string = '';

  selectedPlaylist: any = {}; // New property to store the selected playlist details (New Update)
  playlistSongs: any[] = []; // New property to store playlist songs(New Update)


  private searchSubject = new Subject<string>();



  constructor(
    private playlistService: PlaylistService,
    private route: ActivatedRoute,
    private router: Router // Inject the Router service
  ) {


    
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
      this.loadPlaylists();
    });

    this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(query => this.playlistService.searchSongs(this.userId, query))
    ).subscribe(
      (response: any) => {
        console.log('API Response:', response);
    
        // Check if there are items in the tracks property
        if (response.tracks && response.tracks.items) {
          this.searchResults = response.tracks.items;
          console.log('Debug: Search Results -', this.searchResults);
        } else {
          console.warn('API response is missing expected structure:', response);
        }
      },
      error => {
        console.error('Failed to search songs:', error);
      }
    );
    

  
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
  this.loadPlaylistSongs(this.selectedPlaylistId);// New Update
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

// Function to handle the search input
searchSongs(): void {
  console.log('Before searchSubject.next:', this.searchQuery);
  this.searchSubject.next(this.searchQuery);
  console.log('After searchSubject.next:', this.searchQuery);
  
  // Debug statement for search results
  console.log('Debug: Search Results -', this.searchResults);
  
  // Reset selectedSongId when a new search is performed
  this.selectedSongId = '';
}


// Function to update the URL when a song is selected

onSongSelected(): void {
  // Check if a song is selected
  if (this.selectedSongId) {
    // Update the URL with the selected trackId
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { trackId: this.selectedSongId },
      queryParamsHandling: 'merge'
    });
  }
}


 // Updated method to add songs to the playlist
  addSongsToPlaylist(): void {
    // Check if the required parameters are available
    if (this.playlistId && this.userId && this.selectedSongId) {
      // Call the service method to add songs to the playlist
      this.playlistService.addSongsToPlaylist(this.playlistId, this.userId, this.selectedSongId).subscribe(
        response => {
          console.log('Songs added successfully:', response);
          // Handle success, e.g., show a success message to the user
        },
        error => {
          console.error('Failed to add songs to playlist:', error);
          // Handle error, e.g., show an error message to the user
        }
      );
    } else {
      // Handle invalid input or missing parameters, e.g., show a validation message
    }
  }
  deleteSongsFromPlaylist(): void {
    // Check if the required parameters are available
    if (this.playlistId && this.userId && this.selectedSongId) {
      // Call the service method to add songs to the playlist
      this.playlistService.deleteSongsFromPlaylistO(this.playlistId, this.userId, this.selectedSongId).subscribe(
        response => {
          console.log('Songs deleted from playlist successfully:', response);
          // Handle success, e.g., show a success message to the user
        },
        error => {
          console.error('Failed to delete songs from playlist:', error);
          // Handle error, e.g., show an error message to the user
        }
      );
    } else {
      // Handle invalid input or missing parameters, e.g., show a validation message
    }
  }


  private loadPlaylistSongs(playlistId: string): void {
    if (this.userId && playlistId) {
      // Call the service method to get songs for the playlist
      this.playlistService.getPlaylistSongs(this.userId, playlistId).subscribe(
        (playlistSongsResponse: any) => {
          // Extract the items array from the tracks property
          const playlistItems = playlistSongsResponse.tracks.items;

          // Check if there are items in the playlist
          if (playlistItems && playlistItems.length > 0) {
            // Map the items array to populate the playlistSongs array
            this.playlistSongs = playlistItems.map((item: any) => ({
              id: item.track.id,
              name: item.track.name,
              // Add other properties as needed
            }));

            console.log('Playlist Songs:', this.playlistSongs);
          } else {
            console.warn('No songs found in the playlist.');
          }
        },
        (error) => {
          console.error('Failed to load playlist songs:', error);
        }
      );
    }
  }

}