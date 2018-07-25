import {Component, OnInit} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'dddd';
  text = "";
  constructor(
    private http: HttpClient){};

  ngOnInit(): void {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': 'localhost' })
    };
    this.http.get('http://localhost:88/auth/login',httpOptions).subscribe(data => {
      console.log(data);
    });
  }
}
