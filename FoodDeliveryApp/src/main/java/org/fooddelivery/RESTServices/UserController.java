package org.fooddelivery.RESTServices;


import org.fooddelivery.DomainModel.Users.*;
import org.fooddelivery.Services.Repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final CourierRepository courierRepository;
    private final RestaurantAdminRepository restaurantAdminRepository;

    public UserController(UserRepository userRepository, ClientRepository clientRepository,
                          CourierRepository courierRepository, RestaurantAdminRepository restaurantAdminRepository) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.courierRepository = courierRepository;
        this.restaurantAdminRepository = restaurantAdminRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/clients/create")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientRepository.save(client);
        return ResponseEntity.ok(savedClient);
    }

    @PostMapping("/couriers/create")
    public ResponseEntity<Courier> createCourier(@RequestBody Courier courier) {
        Courier savedCourier = courierRepository.save(courier);
        return ResponseEntity.ok(savedCourier);
    }

    @PostMapping("/restaurant-admins/create")
    public ResponseEntity<RestaurantAdmin> createRestaurantAdmin(@RequestBody RestaurantAdmin admin) {
        RestaurantAdmin savedAdmin = restaurantAdminRepository.save(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());
                    user.setPhoneNumber(updatedUser.getPhoneNumber());
                    user.setUserType(updatedUser.getUserType());
                    Users savedUser = userRepository.save(user);
                    return ResponseEntity.ok(savedUser);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/couriers/{id}")
    public ResponseEntity<Courier> getCourierById(@PathVariable Long id) {
        Optional<Courier> courier = courierRepository.findById(id);
        return courier.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/restaurant-admins/{id}")
    public ResponseEntity<RestaurantAdmin> getRestaurantAdminById(@PathVariable Long id) {
        Optional<RestaurantAdmin> admin = restaurantAdminRepository.findById(id);
        return admin.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
