import { Component, OnInit } from '@angular/core';
import { ArtistsService } from './artists.service';
import { Subject } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-artists',
  templateUrl: './artists.component.html',
  styleUrls: ['./artists.component.css']
})
export class ArtistsComponent implements OnInit {
  userId: string = '';
  searchQuery: string = '';
  selectedArtistId: string = '';
  selectedAlbumId: string = '';
  searchResults: any[] = [];
  albumResults: any[] = [];
  songResults: any[] = [];
  selectedSongId: string = ''; // Add this line
  private searchSubject = new Subject<string>();

  constructor(private artistsService: ArtistsService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
      this.selectedArtistId = params['artistId'];
      this.selectedAlbumId = params['albumId'];

      // Fetch albums based on selectedArtistId if needed
     
    });

    this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(query => this.artistsService.searchArtist(this.userId, query))
    ).subscribe(
      (response: any) => {
        if (response.artists && response.artists.items) {
          this.searchResults = response.artists.items;
        } else {
          console.warn('API response is missing expected structure:', response);
        }
      },
      error => {
        console.error('Failed to search songs:', error);
      }
    );
  }

  // Function to handle the search input
  searchArtists(): void {
    this.searchSubject.next(this.searchQuery);
    this.selectedArtistId = ''; // Reset selectedArtistId when a new search is performed
    this.selectedAlbumId = ''; // Reset selectedAlbumId when a new search is performed
  }

  // Function to handle the fetching of albums
  getAlbums(): void {
    if (this.selectedArtistId) {
      this.artistsService.getAlbumsByArtistId(this.selectedArtistId, this.userId).subscribe(
        (response: any) => {
          if (response && response.length > 0) {
            this.albumResults = response;
          } else {
            console.warn('No albums found for the selected artist.');
          }
        },
        error => {
          console.error('Failed to get albums:', error);
        }
      );
    } else {
      console.warn('No artist selected. Please select an artist first.');
    }
  }

  // Function to handle the selection of an artist
  onArtistSelected(): void {
    if (this.selectedArtistId) {
      this.router.navigate([], {
        relativeTo: this.route,
        queryParams: { artistId: this.selectedArtistId },
        queryParamsHandling: 'merge'
      });
   
    }
  }

  // Function to handle the selection of an album
  onAlbumSelected(): void {
    if (this.selectedAlbumId) {
      this.router.navigate([], {
        relativeTo: this.route,
        queryParams: { albumId: this.selectedAlbumId },
        queryParamsHandling: 'merge'
      });
    }
  }


  getSongsFromAlbum(): void {
    if (this.selectedAlbumId) {
      this.artistsService.getAlbumTracks(this.selectedAlbumId, this.userId).subscribe(
        (response: any) => {
          if (response && response.length > 0) {
            this.songResults = response;
          } else {
            console.warn('No songs found for the selected album.');
          }
        },
        error => {
          console.error('Failed to get songs:', error);
        }
      );
    } else {
      console.warn('No album selected. Please select an album first.');
    }
  }



  onSongSelected(): void {
    if (this.selectedSongId) {
      this.router.navigate([], {
        relativeTo: this.route,
        queryParams: { songId: this.selectedSongId },
        queryParamsHandling: 'merge'
      });
    }
  }
}
