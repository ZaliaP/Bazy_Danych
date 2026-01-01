package com.example.application.services;

import com.example.application.data.Company;
import com.example.application.data.Contact;
import com.example.application.data.Status;
import com.example.application.data.CompanyRepository;
import com.example.application.data.ContactRepository;
import com.example.application.data.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;
    private final StatusRepository statusRepository;

    public CrmService(ContactRepository contactRepository, CompanyRepository companyRepository, StatusRepository statusRepository) {
        this.companyRepository = companyRepository;
        this.contactRepository = contactRepository;
        this.statusRepository = statusRepository;
    }

    public List<Contact> findAllContacts(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return contactRepository.findAll();
        }else{
            return contactRepository.search(filterText); //search to jest metoda stworzona w którymś interefjsie
        }
    }

    public long coutContacts(){
        return contactRepository.count();
    }

    public void deleteContact(Contact contact){
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact){
        if(contact == null){
            System.err.println("Contact is null");
            return;
        }
        contactRepository.save(contact);
    }

    public List<Company> findAllCompanies(){
        return companyRepository.findAll();
    }

    public List<Status> findAllStatus(){
        return statusRepository.findAll();
    }
}
