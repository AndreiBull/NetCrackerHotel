package by.netcracker.hotel.controllers.pagination;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.paginationspring.controller.PaginationControllerAbstract;

import by.netcracker.hotel.entities.pagination.HotelSearchParam;
import by.netcracker.hotel.services.HotelService;
import by.netcracker.hotel.services.impl.pagination.HotelPaginationService;

@Controller
@RequestMapping("/admin")
public class HotelPaginationController extends PaginationControllerAbstract<HotelSearchParam> {
    private static Logger log = Logger.getLogger(HotelPaginationController.class);

    private HotelPaginationService hotelPaginationService;
    private HotelService hotelService;

    public HotelPaginationController() {
        setOptionWidth(750);
        setDefaultRecordPerPage(10);
        setDefaultSortAscDesc("d");
        setPageLink("/admin/pagination/list_of_hotels_ajax");
        setAjax(true);
    }

    @Autowired
    public void setPaginationService(HotelPaginationService hotelPaginationService, HotelService hotelService) {
        super.setPaginationService(hotelPaginationService);
        this.hotelPaginationService = hotelPaginationService;
        this.hotelService = hotelService;
    }

    @RequestMapping(value = "/list_of_hotels", method = { RequestMethod.GET, RequestMethod.POST })
    public String defineJsp(@ModelAttribute(PPARAM) HotelSearchParam pparam, Model model) throws Exception {
        Map<String, Object> map = assignModel(pparam, null, false);
        model.addAllAttributes(map);
        model.addAttribute("hotels", hotelService.getHotelNames());
        return "admin/pagination/list_of_hotels";
    }

    @RequestMapping(value = "/list_of_hotels/{num}", method = { RequestMethod.GET, RequestMethod.POST })
    public String defineJsp(@ModelAttribute(PPARAM) HotelSearchParam pparam, @PathVariable("num") int num, Model model)
        throws Exception {
        setDefaultRecordPerPage(num);
        hotelPaginationService.setPageNum(num);
        Map<String, Object> map = assignModel(pparam, null, false);
        model.addAllAttributes(map);
        model.addAttribute("hotels", hotelService.getHotelNames());
        return "admin/pagination/list_of_hotels";
    }

    @RequestMapping(value = "pagination/list_of_hotels_ajax", method = { RequestMethod.GET, RequestMethod.POST })
    public String defineAjaxJsp(@ModelAttribute(PPARAM) HotelSearchParam pparam,
        @RequestParam(value = BUTTON_ACTION, required = false) String buttonAction, Model model) throws Exception {
        hotelPaginationService.deleteButtonAction(pparam, buttonAction);
        Map<String, Object> map = assignModel(pparam, buttonAction);
        model.addAllAttributes(map);

        return "admin/pagination/list_of_hotels_ajax";
    }
}