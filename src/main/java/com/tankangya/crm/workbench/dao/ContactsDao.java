package com.tankangya.crm.workbench.dao;

import com.tankangya.crm.workbench.entity.Contacts;

import java.util.List;

public interface ContactsDao {

    int saveContacts(Contacts contacts);

    List<Contacts> getContactsList(String fullname);
}
