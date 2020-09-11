package com.tankangya.crm.workbench.service.impl;

import com.tankangya.crm.settings.dao.UserDao;
import com.tankangya.crm.settings.entity.User;
import com.tankangya.crm.utils.DateTimeUtil;
import com.tankangya.crm.utils.UUIDUtil;
import com.tankangya.crm.vo.PaginationVO;
import com.tankangya.crm.workbench.dao.*;
import com.tankangya.crm.workbench.entity.*;
import com.tankangya.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("myClueService")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;

    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    private ClueActivityRelation clueActivityRelation;

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;

    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Override
    public List<User> getUserList() {
        List<User> list = userDao.testSelectUser();
        return list;
    }

    @Override
    public PaginationVO<Clue> getPageList(Map<String, Object> map) {

        int total = clueDao.getPageListCount(map);
        List<Clue> datalist = clueDao.getPageList(map);

        PaginationVO paginationVO = new PaginationVO();
        paginationVO.setTotal(total);
        paginationVO.setDatalist(datalist);

        return paginationVO;
    }

    @Override
    public boolean saveClue(Clue clue) {
        boolean flag = false;
        int count = clueDao.saveClue(clue);
        if (count != 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean deleteClue(String[] para) {
        boolean flag = true;
        int count1 = clueRemarkDao.getClueRemarkById(para);
        int count2 = clueRemarkDao.deleteClueRemarkById(para);
        if (count1 != count2) {
            flag = false;
        }
        int count3 = clueDao.deleteClue(para);
        if (count3 != para.length) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> editClue(String id) {
        //先查询出user
        List<User> uList = userDao.testSelectUser();
        //查询出要修改的记录
        Clue a = clueDao.editClue(id);
        //封装到map
        Map<String, Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);
        return map;
    }

    @Override
    public boolean updateClue(Clue clue) {

        boolean flag = false;

        int count = clueDao.updateClue(clue);
        if (count != 0) {
            flag = true;
        }

        return flag;
    }

    @Override
    public Clue detail(String id) {

        Clue clue = clueDao.detail(id);
        return clue;
    }

    @Override
    public List<ClueRemark> getClueRemark(String id) {
        List<ClueRemark> list = clueRemarkDao.getClueRemark(id);
        return list;
    }

    @Override
    public boolean deleteClueRemark(String id) {
        boolean flag = true;

        int count = clueRemarkDao.deleteClueRemark(id);
        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean updateClueRemark(ClueRemark clueRemark) {
        boolean flag =true;

        int count = clueRemarkDao.updateClueRemark(clueRemark);
        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean saveClueRemark(ClueRemark clueRemark) {
        boolean flag = true;

        int count = clueRemarkDao.saveClueRemark(clueRemark);
        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean deleteCLueActivityRelationById(String id) {
        boolean flag = true;

        int count = clueActivityRelationDao.deleteCLueActivityRelationById(id);
        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean saveClueActicityRaletionBttn(String[] id, String clueId) {
        boolean flag = true;

        for (String ids : id) {
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityId(ids);
            clueActivityRelation.setClueId(clueId);
            int count = clueActivityRelationDao.saveClueActicityRaletionBttn(clueActivityRelation);
            if (count != 1) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean saveConvert(String clueId, String createBy, Tran tran) {
        boolean flag = true;
        String createTime = DateTimeUtil.getSysTime();
        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue clue = clueDao.getClueById(clueId);
        String name = clue.getCompany();

        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        Customer customer = customerDao.getCustomerByCompany(name);
        if (customer == null) {

            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setName(name);
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setContactSummary(clue.getContactSummary());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());

            int count1 = customerDao.saveCustomer(customer);
            if (count1 != 1) {
                flag = false;
            }
        }
        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setBirth(null);
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());
        int count2 = contactsDao.saveContacts(contacts);
        if (count2 != 1) {
            flag = false;
        }
        //(4) 线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList = clueRemarkDao.getClueRemark(clueId);
        for (ClueRemark clueRemark: clueRemarkList) {
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            int count3 = customerRemarkDao.saveCustomerRemark(customerRemark);
            if (count3 != 1) {
                flag = false;
            }

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(contacts.getId());
            int count4 = contactsRemarkDao.saveContactsRemark(contactsRemark);
            if (count4 != 1) {
                flag = false;
            }
        }
        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getClueActivityRelation(clueId);
        for (ClueActivityRelation clueActivityRelation: clueActivityRelationList) {
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());

            int count5 = contactsActivityRelationDao.saveContactsActivityRelation(contactsActivityRelation);
            if (count5 != 1) {
                flag = false;
            }
        }
        //(6) 如果有创建交易需求，创建一条交易
        if (tran != null) {

            /*

                t对象在controller里面已经封装好的信息如下：
                    id,money,name,expectedDate,stage,activityId,createBy,createTime

                接下来可以通过第一步生成的c对象，取出一些信息，继续完善对t对象的封装

             */

            tran.setOwner(clue.getOwner());
            tran.setCustomerId(customer.getId());
            tran.setType("新业务");
            tran.setSource(clue.getSource());
            tran.setDescription(clue.getDescription());
            tran.setContactsId(contacts.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setNextContactTime(clue.getNextContactTime());
            int count6 = tranDao.saveTran(tran);
            if (count6 != 1) {
                flag = false;
            }

            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setTranId(tran.getId());
            int count7 = tranHistoryDao.savetranHistory(tranHistory);
            if (count7 != 1) {
                flag = false;
            }
        }

        //(8) 删除线索备注
        for (ClueRemark clueRemark: clueRemarkList) {
            int count8 = clueRemarkDao.delete(clueRemark);
            if (count8 != 1) {
                flag = false;
            }
        }
        //(9) 删除线索和市场活动的关系
        for (ClueActivityRelation clueActivityRelation: clueActivityRelationList) {
            int count9 = clueActivityRelationDao.delete(clueActivityRelation);
            if (count9 != 1) {
                flag = false;
            }
        }
        //(10) 删除线索
        int count10 = clueDao.delete(clueId);
        if (count10 != 1) {
            flag = false;
        }
        return flag;

    }


}
