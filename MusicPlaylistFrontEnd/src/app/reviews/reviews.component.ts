import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewService } from './reviews.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit{
  entityType: string = '';
  userId: string = '';
  entityId: string = '';
  reviewId: string = '';
  reviewComment: string = '';
  reviewName: string = '';
  reviewRating: number = 0.0;
  reviews: any[] = [];

  constructor(
    private reviewService: ReviewService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void 
  {
    this.route.queryParams.subscribe((params) => {
      this.userId = params['id'];
      this.reviewService.getReviews(this.userId).subscribe(
        (data: any[]) => {
          this.reviews = data;
        },
        (error) => {
          console.error('Error fetching reviews:', error);
        }
      );
    });
  }

  createReview(): void {
    if (this.userId && this.entityType && this.entityId && (this.reviewName || this.reviewComment || this.reviewRating)) {
      const createReviews = {
        name: this.reviewName,
        comment: this.reviewComment,
        rating: this.reviewRating
      };
      // Include the selected entity type in the URL
      const url = `/${this.entityType}/${this.entityId}/review?userId=${this.userId}`;

      this.reviewService.postReview(url, createReviews).subscribe(
        response => {
          console.log('Review created successfully:', response);
          // Handle success, e.g., show a success message to the user
        },
        error => {
          console.error('Failed to create review:', error);
          // Handle error, e.g., show an error message to the user
        }
      );
    } else {
      // Handle invalid input or missing userId, e.g., show a validation message
    }
  }

  updateReview(): void {
    if (this.userId && this.entityType && this.entityId && this.reviewId && (this.reviewName || this.reviewComment || this.reviewRating) ) {
      const updateReviews = {
        name: this.reviewName,
        comment: this.reviewComment,
        rating: this.reviewRating
      };
      // Include the selected entity type in the URL
      const url = `/${this.entityType}/${this.entityId}/review/${this.reviewId}?userId=${this.userId}`;

      this.reviewService.updateReview(url, updateReviews).subscribe(
        response => {
          console.log('Review updated successfully:', response);
          // Handle success, e.g., show a success message to the user
        },
        error => {
          console.error('Failed to update review:', error);
          // Handle error, e.g., show an error message to the user
        }
      );
    } else {
      // Handle invalid input or missing userId, e.g., show a validation message
    }
  }

  deleteReview(): void {
    if (this.userId && this.entityType && this.entityId && this.reviewId) {

      // Include the selected entity type in the URL
      const url = `/${this.entityType}/${this.entityId}/review/${this.reviewId}?userId=${this.userId}`;

      this.reviewService.deleteReview(url).subscribe(
        response => {
          console.log('Review deleted successfully:', response);
          // Handle success, e.g., show a success message to the user
        },
        error => {
          console.error('Failed to delete review:', error);
          // Handle error, e.g., show an error message to the user
        }
      );
    } else {
      // Handle invalid input or missing userId, e.g., show a validation message
    }
  }

  getReviews(): void {
    if (this.userId && this.entityType && this.entityId) {

      // Include the selected entity type in the URL
      const url = `/${this.entityType}/${this.entityId}/reviews?userId=${this.userId}`;

      this.reviewService.getReviews(url).subscribe(
        response => {
          console.log('Review deleted successfully:', response);
          // Handle success, e.g., show a success message to the user
        },
        error => {
          console.error('Failed to delete review:', error);
          // Handle error, e.g., show an error message to the user
        }
      );
    } else {
      // Handle invalid input or missing userId, e.g., show a validation message
    }
  }
}
