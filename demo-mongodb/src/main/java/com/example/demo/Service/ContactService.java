package com.example.demo.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Contact;
import com.example.demo.Repository.ContactRepository;
import com.example.demo.common.ItemColsExcelDto;
import com.example.demo.constant.CommonConstant;
import com.example.demo.dto.ContactExportDto;
import com.example.demo.enumDef.ContactEnum;
import com.example.demo.utils.ExportExcelUtil;
import com.example.demo.utils.ImportExcelUtil;
import com.example.demo.utils.common.CommonNullAwareBeanUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ContactService {
	@Autowired
	private ContactRepository contactRepository;

	public List<Contact> getAllContacts() {
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

	public void exportFileExcel(HttpServletResponse httpServletResponse, Locale locale) {
		try {
			String templateName = CommonConstant.CONTACT_TEMPLATE;
			String template = CommonConstant.REAL_PATH_TEMPLATE_EXCEL + templateName + CommonConstant.TYPE_EXCEL;
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			try (InputStream inputStream = classLoader.getResourceAsStream(template);) {
				List<Contact> lstDatas = this.getAllContacts();
				List<ContactExportDto> lstDataConver = new ArrayList<>();
				int stt = 1;
				for (Contact item : lstDatas) {
					ContactExportDto dto = new ContactExportDto();
					try {
						CommonNullAwareBeanUtil.copyPropertiesWONull(item, dto);
						dto.setId(item.get_id().toString());
						dto.setStt(stt);
					} catch (Exception e) {
						log.error("CommonNullAwareBeanUtil.copyPropertiesWONull | ERROR | " + e);
					}
					lstDataConver.add(dto);
					stt++;
				}
				String date = "dd/mm/yyyy";
				List<ItemColsExcelDto> cols = new ArrayList<>();
				// start fill data to work
				ImportExcelUtil.setListColumnExcel(ContactEnum.class, cols);
				ExportExcelUtil<ContactExportDto> exportExcel = new ExportExcelUtil<>();

				// do export
				exportExcel.exportExcelWithXSSFNonPass(inputStream, locale, lstDataConver, ContactExportDto.class, cols, date,
						httpServletResponse, templateName);
			}
		} catch (Exception e) {
			log.error("ContactService_exportFileExcel || ERROR", e);
		}
	}
}
