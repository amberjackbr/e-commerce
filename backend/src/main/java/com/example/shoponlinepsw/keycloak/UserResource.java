package com.example.shoponlinepsw.keycloak;

import java.util.*;

import com.example.shoponlinepsw.entities.Buyer;
import com.example.shoponlinepsw.entities.Cart;
import com.example.shoponlinepsw.entities.Seller;
import com.example.shoponlinepsw.services.BuyerService;
import com.example.shoponlinepsw.services.SellerService;
import com.example.shoponlinepsw.support.exceptions.MailAlredyExistException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.ws.rs.core.Response;

@RestController
@RequestMapping("/keycloak")
@SecurityRequirement(name = "keycloak")
public class UserResource {

    @Autowired
    KeycloakSecurityUtil keycloakUtil;

    @Autowired
    BuyerService buyerService;

    @Autowired
    SellerService sellerService;

    @Value("${realm}")
    private String realm;

    @GetMapping
    @RequestMapping("/users")
    public List<User> getUsers() {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        List<UserRepresentation> userRepresentations =
                keycloak.realm(realm).users().list();
        return mapUsers(userRepresentations);
    }

    @GetMapping(value = "/users/{id}")
    public User getUser(@PathVariable("id") String id) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        return mapUser(keycloak.realm(realm).users().get(id).toRepresentation());
    }

    @PostMapping(value = "/user")
    public Response createUser(User user) {
        UserRepresentation userRep = mapUserRep(user);
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        Response res = keycloak.realm(realm).users().create(userRep);
        return Response.ok(user).build();
    }

    //PROVA BUYER
    @PostMapping(value = "/createbuyer")
    @Transactional
    public Response createBuyer(@RequestBody Request request) throws MailAlredyExistException {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getFirstName());
        UserRepresentation userRep = mapUserRep(user);
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        Response res = keycloak.realm(realm).users().create(userRep);
        RoleRepresentation role = keycloak.realm(realm).roles().get(request.getRole()).toRepresentation();
        List<UserRepresentation> usr = keycloak.realm(realm).users().searchByEmail(request.getEmail(), true);
        String usrId = usr.get(0).getId();
        keycloak.realm(realm).users().get(usrId).roles().realmLevel().add(Arrays.asList(role));

        if(request.getRole().equals("buyer")){
            Buyer buyer = new Buyer();
            buyer.setFirstName(request.getFirstName());
            buyer.setLastName(request.getLastName());
            buyer.setEmail(request.getEmail());
            buyer.setTelephoneNumber(request.getPhoneNumber());
            try{
                Buyer added = buyerService.registerNewUser(buyer);
                Cart cart = new Cart();
                cart.setBuyer(added);
                Cart newCart = buyerService.createNewCart(cart);
                added.setCart(newCart);
            }catch(MailAlredyExistException e){
                throw new MailAlredyExistException();
            }
            return Response.ok(user).build();
        }
        if(request.getRole().equals("seller")){
            Seller seller = new Seller();
            seller.setFirstName(request.getFirstName());
            seller.setLastName(request.getLastName());
            seller.setEmail(request.getEmail());
            seller.setTelephoneNumber(request.getPhoneNumber());
            sellerService.registerNewSeller(seller);
            return Response.ok(user).build();
        }
        /*Buyer buyer = new Buyer();
        buyer.setEmail(request.getEmail());
        buyer.setFirstName(request.getFirstName());
        buyer.setLastName(request.getLastName());
        buyer.setEmail(request.getEmail());
        buyer.setTelephoneNumber(request.getPhoneNumber());
        try{
            buyerService.registerNewUser(buyer);
        }catch(MailAlredyExistException e){
            throw new MailAlredyExistException();
        }*/
        return Response.ok(user).build();
    }

    @PutMapping(value = "/user")
    public Response updateUser(User user) {
        UserRepresentation userRep = mapUserRep(user);
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        keycloak.realm(realm).users().get(user.getId()).update(userRep);
        return Response.ok(user).build();
    }

    @DeleteMapping(value = "/users/{id}")
    public Response deleteUser(@PathVariable("id") String id) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        keycloak.realm(realm).users().delete(id);
        return Response.ok().build();
    }

    @GetMapping(value = "/users/{id}/roles")
    public List<Role> getRoles(@PathVariable("id") String id) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        return RoleResource.mapRoles(keycloak.realm(realm).users()
                .get(id).roles().realmLevel().listAll());
    }

    @PostMapping(value = "/users/{id}/roles/{roleName}")
    public Response createRole(@PathVariable("id") String id,
                               @PathVariable("roleName") String roleName) {
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        RoleRepresentation role = keycloak.realm(realm).roles().get(roleName).toRepresentation();
        keycloak.realm(realm).users().get(id).roles().realmLevel().add(Arrays.asList(role));
        return Response.ok().build();
    }

    private List<User> mapUsers(List<UserRepresentation> userRepresentations) {
        List<User> users = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(userRepresentations)) {
            userRepresentations.forEach(userRep -> {
                users.add(mapUser(userRep));
            });
        }
        return users;
    }

    private User mapUser(UserRepresentation userRep) {
        User user = new User();
        user.setId(userRep.getId());
        user.setFirstName(userRep.getFirstName());
        user.setLastName(userRep.getLastName());
        user.setEmail(userRep.getEmail());
        user.setUserName(userRep.getUsername());
        return user;
    }

    private UserRepresentation mapUserRep(User user) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setId(user.getId());
        userRep.setUsername(user.getUserName());
        userRep.setFirstName(user.getFirstName());
        userRep.setLastName(user.getLastName());
        userRep.setEmail(user.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);
        List<CredentialRepresentation> creds = new ArrayList<>();
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setValue(user.getPassword());
        creds.add(cred);
        userRep.setCredentials(creds);
        return userRep;
    }

}
