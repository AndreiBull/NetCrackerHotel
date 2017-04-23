package by.netcracker.hotel.dao.impl.pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.paginationspring.bo.BoPaginationParam;

import by.netcracker.hotel.dao.UserDAO;
import by.netcracker.hotel.entities.User;
import by.netcracker.hotel.entities.pagination.UserSearchParam;

@Repository
public class UserPaginationDAO implements com.github.paginationspring.dao.PaginationDao {
    UserDAO userDAO;

    @Autowired
    public UserPaginationDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public int retrieveCountResult(BoPaginationParam pparam) throws Exception {
        System.out.println(pparam);
        UserSearchParam param = (UserSearchParam) pparam;
        List<User> list = userDAO.getAll();
        if (param.getAuthority() != null && !param.getAuthority().equals("")) {
            List<User> list1 = new ArrayList<User>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getAuthority().toString().equals(param.getAuthority())) {
                    list1.add(list.get(i));
                }
            }
            list = list1;
        }
        if (param.getEnabled() != null && !param.getEnabled().equals("")) {
            List<User> list1 = new ArrayList<User>();
            for (int i = 0; i < list.size(); i++) {
                if (((Boolean) list.get(i).getEnabled()).toString().equals(param.getEnabled())) {
                    list1.add(list.get(i));
                }
            }
            list = list1;
        }
        if (param.getUsername() != null && !param.getUsername().equals("")) {
            List<User> list1 = new ArrayList<User>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUsername().contains((param.getUsername()))) {
                    list1.add(list.get(i));
                }
            }
            list = list1;
        }
        return list.size();
    }

    @Override
    public List retrievePageResult(BoPaginationParam pparam) throws Exception {
        List<User> list = userDAO.getAll();

        int index = Integer.parseInt(pparam.getResultIndex());

        UserSearchParam param = (UserSearchParam) pparam;
        if (param.getAuthority() != null && !param.getAuthority().equals("")) {
            List<User> list1 = new ArrayList<User>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getAuthority().toString().equals(param.getAuthority())) {
                    list1.add(list.get(i));
                }
            }
            list = list1;
        }
        if (param.getEnabled() != null && !param.getEnabled().equals("")) {
            List<User> list1 = new ArrayList<User>();
            for (int i = 0; i < list.size(); i++) {
                if (((Boolean) list.get(i).getEnabled()).toString().equals(param.getEnabled())) {
                    list1.add(list.get(i));
                }
            }
            list = list1;
        }
        if (param.getUsername() != null && !param.getUsername().equals("")) {
            List<User> list1 = new ArrayList<User>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUsername().contains((param.getUsername()))) {
                    list1.add(list.get(i));
                }
            }
            list = list1;
        }

        List<User> list1 = new ArrayList<User>();
        for (int i = index; i < index + 10; i++) {
            if (list.size() > i) {
                list1.add(list.get(i));
            }
        }

        return list1;
    }

}
