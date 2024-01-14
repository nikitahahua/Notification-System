package com.notyficationsystem.NotyficationSystem.rest;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.service.ContactService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
@PreAuthorize("hasAuthority('ADMIN')")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/create")
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        Contact createdContact = contactService.create(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @PutMapping("/update")
    public ResponseEntity<Contact> updateContact(@Valid @RequestBody Contact contact) {
        Contact updatedContact = contactService.update(contact);
        return ResponseEntity.ok(updatedContact);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Contact contact = contactService.readById(id);
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Contact> getContactByEmail(@PathVariable String email) {
        Contact contact = contactService.readByEmail(email);
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/name")
    public ResponseEntity<Contact> getContactByFullName(@RequestParam String fullname) {
        Contact contact = contactService.readByFullName(fullname);
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactService.getAll();
        return ResponseEntity.ok(contacts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
