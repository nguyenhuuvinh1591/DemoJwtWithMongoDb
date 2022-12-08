package com.example.demo.Rest;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.example.demo.Entity.Contact;
import com.example.demo.Service.ContactService;
import com.example.demo.base.AbstractRest;
import com.example.demo.common.DtsApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/contact")
@EnableCaching
@Slf4j
public class ContactRest extends AbstractRest {

    @Autowired
    private ContactService contactService;

    private AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public DtsApiResponse getAllContacts() {
        long start = System.currentTimeMillis();
        try {
            List<Contact> list = contactService.getAllContacts();
            return successHandler.handlerSuccess(list, start);
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start);
        }
    }

    @Cacheable("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DtsApiResponse getContactById(@PathVariable("id") ObjectId id) {
        long start = System.currentTimeMillis();
        try {
            Contact contact = contactService.getContactById(id);
            Optional<Contact> optionalContact = Optional.ofNullable(contact);
            return successHandler.handlerSuccess(optionalContact, start);
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public DtsApiResponse modifyContactById(@PathVariable("id") ObjectId id, @Valid @RequestBody Contact contact) {
        long start = System.currentTimeMillis();
        try {
            Contact result = contactService.modifyContactById(id, contact);
            return successHandler.handlerSuccess(result, start);
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public DtsApiResponse createContact(@Valid @RequestBody Contact contact) {
        long start = System.currentTimeMillis();
        try {
            Contact result = contactService.createContact(contact);
            return successHandler.handlerSuccess(result, start);
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DtsApiResponse deleteContact(@PathVariable ObjectId id) {
        long start = System.currentTimeMillis();
        try {
            contactService.deleteContact(id);
            return successHandler.handlerSuccess(id, start);
        } catch (Exception e) {
            return this.errorHandler.handlerException(e, start);
        }
    }

    @GetMapping("/export-excel")
    public void getExportExcel(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Long start = System.currentTimeMillis();
        try {
            Locale locale = acceptHeaderLocaleResolver.resolveLocale(httpServletRequest);
            contactService.exportFileExcel(httpServletResponse, locale);
        } catch (Exception ex) {
            this.errorHandler.handlerException(ex, start);
        }
    }
}