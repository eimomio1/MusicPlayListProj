import { Component, OnInit } from '@angular/core';
import { ArtistsService } from './artists.service';
import { Subject } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router'; // Import Router
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

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
  selector: 'app-artists',
  templateUrl: './artists.component.html',
  styleUrls: ['./artists.component.css']
})

export class ArtistsComponent implements OnInit {
  userId: string = '';
  searchQuery: string = '';
  selectedAlbumId: string = '';
  searchResults: any[] = [];
  private searchSubject = new Subject<string>();

  constructor(private artistsService: ArtistsService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
    });

    this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(query => this.artistsService.searchSongs(this.userId, query))
    ).subscribe(
      (response: any) => {
        console.log('API Response:', response);

        // Check if there are items in the tracks property
        if (response.artists && response.artists.items) {
          this.searchResults = response.artists.items;
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


  // Function to handle the search input
searchArtists(): void {
  console.log('Before searchSubject.next:', this.searchQuery);
  this.searchSubject.next(this.searchQuery);
  console.log('After searchSubject.next:', this.searchQuery);
  
  // Debug statement for search results
  console.log('Debug: Search Results -', this.searchResults);
  
  // Reset selectedSongId when a new search is performed
  this.selectedAlbumId = '';
}



onAlbumSelected(): void {
  // Check if a song is selected
  if (this.selectedAlbumId) {
    // Update the URL with the selected trackId
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { artistId: this.selectedAlbumId },
      queryParamsHandling: 'merge'
    });
  }
}


getAlbums(): void {
  if (this.selectedAlbumId) {
    // Extract the artistId from the URL
    const artistId = this.route.snapshot.queryParamMap.get('artistId');

    if (artistId) {
      // Call the backend endpoint to get albums by artist ID
      this.artistsService.getAlbumsByArtistId(artistId, this.userId).subscribe(
        (albums: any[]) => {
          // Update the searchResults with the albums
          this.searchResults = albums;
          console.log('Debug: Albums -', this.searchResults);
        },
        error => {
          console.error('Failed to get albums:', error);
        }
      );
    } else {
      console.warn('ArtistId not found in the URL.');
    }
  }
}

}