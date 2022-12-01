package com.example.demo.Service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Contact;
import com.example.demo.Repository.ContactRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContactService {
	@Autowired
	private ContactRepository contactRepository;
	
	public List<Contact> getAllContacts(){
		return contactRepository.findAll();
	}
	
	public Contact getContactById(ObjectId id) {
		return contactRepository.findBy_id(id);
	}
	
	public Contact modifyContactById(ObjectId id, Contact contact) {
		contact.set_id(id);
		return contactRepository.save(contact);
	}
	
	public Contact createContact(Contact contact) {
		contact.set_id(ObjectId.get());
		contactRepository.save(contact);
		return contact;	
	}
	
	public void deleteContact(ObjectId id) {
		contactRepository.delete(contactRepository.findBy_id(id));
	}
}
