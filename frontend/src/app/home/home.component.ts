//import { LogService } from './../services/log.service';
import { Component, OnInit } from '@angular/core';
import { Product, ProductService } from '../services/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {

  products: Product[] = [];
  selectedProduct?: Product;
  private apiurl = 'localhost:8080/'


  constructor(private productService: ProductService){}

  ngOnInit(): void {
    this.productService.getMostSold()
    .subscribe((products: Product[]) =>{
      this.products = products;
    })
  }

  //getProducts(){
  //  this.manager.getProducts().subscribe(products => this.products = products)
  //}

  public metodo(){
    //this.logservice.logmetodo();
  }



}
