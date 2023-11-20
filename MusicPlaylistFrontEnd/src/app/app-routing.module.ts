import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { LoginsComponent } from './logins/logins.component';
import { PlaylistComponent } from './playlist/playlist.component';
import { ReviewsComponent } from './reviews/reviews.component';
const routes: Routes = [
  // Define the route for the "home" component
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'login',
    component: LoginsComponent,
  },
  {
    path: 'playlist',
    component: PlaylistComponent, 
    pathMatch: 'full',
  },
  {
    path: 'reviews',
    component: ReviewsComponent, 
    pathMatch: 'full',
  },
  // Add other routes if needed

  // A generic route that redirects to 'home' if the URL doesn't match any defined route
  {
    path: '**',
    redirectTo: 'home',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), HttpClientModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
