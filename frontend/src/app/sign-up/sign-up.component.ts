import { SingUpService } from './../services/sing-up.service';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '../services/sing-up.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {

  firstName: string = '';
  lastName: string = '';
  email: string = '';
  phoneNumber: string = '';
  password: string = '';
  role: string = '';
  possibleRoles: string[]= ['buyer', 'seller'];
  rolesForm: FormGroup;
  default: String = '';
  service: SingUpService = new SingUpService(this.http);


  constructor(private http: HttpClient) {
    this.rolesForm = new FormGroup({
        role: new FormControl('')
    });
    this.rolesForm.controls['role'].setValue(this.default, {onlySelf: true});
}

singUp(firstName: string, lastName: string, email: string, phoneNumber:string, password: string, role: string){
  var user = <User>({
    firstName: this.firstName,
    lastName: this.lastName,
    email: this.email,
    phoneNumber: this.phoneNumber,
    password: this.password,
    role: this.role
  })
  return this.service.requestSignUp(user);
}

}
