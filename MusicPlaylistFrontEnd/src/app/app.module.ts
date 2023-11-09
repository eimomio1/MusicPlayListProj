import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // Add this line

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginsComponent } from './logins/logins.component';
import { HomeComponent } from './home/home.component';
import { PlaylistComponent } from './playlist/playlist.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginsComponent,
    HomeComponent,
    PlaylistComponent
  ],
  imports: [
    BrowserModule,
    FormsModule, // Add this line
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
