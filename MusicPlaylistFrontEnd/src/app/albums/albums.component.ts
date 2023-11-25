import { Component, OnInit } from '@angular/core';
import { AlbumsService } from './albums.service';
import { AuthService } from '../authentication/auth.service';
import { ActivatedRoute, Router } from '@angular/router';

interface ApiResponse {
  albums: any;
  artists: any;
  episodes: any;
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
  selector: 'app-albums',
  templateUrl: './albums.component.html',
  styleUrls: ['./albums.component.css']
})
export class AlbumsComponent implements OnInit {
  yourButtonClickFunction(arg0: any) {

  }
    userId: string = '';
    description: string = '';
    accessToken: string | null | undefined;
    nameOfAlbums: string = '';
    albumId: string = '';
    albums: any[] = [];
    selectedAlbums: any = {};
    albumSongs: any[] = [];
    selectedAlbumId: string = '';
    selectedSongId: string = '';

    constructor(
      private albumsService: AlbumsService,
      private route: ActivatedRoute,
      private router: Router, // Inject the Router service
      private authService: AuthService
    ) {
      this.authService.accessToken$.subscribe((token) => {
        this.accessToken = token;
      });
    }

    ngOnInit(): void {
      this.route.queryParams.subscribe(params => {
        this.userId = params['id'];
        this.accessToken = params['accessToken'];
        this.loadAlbums();
      });

    }

    private loadAlbums(): void {
      this.albumsService.getAlbums(this.userId).subscribe(
        albums => {
          // Update the playlists array with additional information
          this.albums = albums.map(albums => {
            console.log('Albums:', albums);
            return {
              albumName: albums.name,
              albumImage: albums.images?.[0]?.url || null, // Use playlistImage instead of playlistDescription
              spotifyId: albums.id,
            };
          });    
    
          // Extract playlistId from the URL
          this.route.queryParams.subscribe(params => {
            this.selectedAlbumId = params['albumId'] || ''; // Default to an empty string
          });
        },
        error => {
          console.error('Failed to load albums:', error);
        }
      );
    }  

    private loadAlbumSongs(albumId: string): void {
      if (this.userId && albumId) {
        this.albumsService.getAlbumSongs(this.userId, albumId).subscribe(
          (albumSongsResponse: any) => {
            const albumItems = albumSongsResponse.tracks.items;

            if (albumItems && albumItems.length > 0) {
              this.albumSongs = albumItems.map((item: any) => ({
                id: item.track.id,
                name: item.track.name,
              }));

              console.log('Album Songs:', this.albumSongs);
            } else {
              console.warn('No songs found in the albums.');
            }
          },
          (error) => {
            console.error('Failed to load album songs:', error);
          }
        );
      }
    }

    onSelectAlbum(albumId: string): void {
      // Update the URL with the selected albumId
      this.router.navigate([], {
        relativeTo: this.route,
        queryParams: { albumId: albumId },
        queryParamsHandling: 'merge'
      });
  
      // Set the albumId property
      this.albumId = albumId;
      this.loadAlbumSongs(albumId);
    }  

    onAlbumSelected(): void {
      // Update the URL with the selected playlistId
      this.router.navigate([], {
        relativeTo: this.route,
        queryParams: { albumId: this.selectedAlbumId },
        queryParamsHandling: 'merge'
      });

      // Set the playlistId property
      this.albumId = this.selectedAlbumId;
      this.loadAlbumSongs(this.selectedAlbumId);// New Update
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
}
