import { Injectable } from '@angular/core';

export interface Seller{
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  telephoneNumber: string;
}

@Injectable({
  providedIn: 'root'
})
export class SellerService {

  constructor() {}
}

