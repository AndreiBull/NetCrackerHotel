package by.netcracker.hotel.controllers;

import by.netcracker.hotel.dto.UserDTO;
import by.netcracker.hotel.entities.Order;
import by.netcracker.hotel.entities.Room;
import by.netcracker.hotel.entities.User;
import by.netcracker.hotel.entities.VerificationToken;
import by.netcracker.hotel.enums.ROLE;
import by.netcracker.hotel.events.ForgotPasswordEvent;
import by.netcracker.hotel.exceptions.UserNotFoundException;
import by.netcracker.hotel.services.OrderService;
import by.netcracker.hotel.services.UserService;
import by.netcracker.hotel.util.CloudinaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import java.util.Arrays;
import java.util.Date;

import static by.netcracker.hotel.util.CloudinaryUtil.saveFileToCloud;

@Controller
public class UserController {

    @Autowired
    public UserController(ServletContext context,ApplicationEventPublisher eventPublisher) {
        CloudinaryUtil.UPLOADED_FOLDER = context.getRealPath("/resources/img/");
    }

    @Autowired
    private UserService userService;



    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String save(@ModelAttribute("currentUser") User dto, @RequestParam("file") MultipartFile file,Model model) {
        dto.setAvatar(saveFileToCloud(file));
        if(userService.update(dto)){
           model.addAttribute("message","Update successfully");
        } else {
            model.addAttribute("error","Update error");
        }
        return "profile";
    }

    @RequestMapping(value = "/log-out", method = RequestMethod.POST)
    public ModelAndView logOut() {
        ModelAndView model = new ModelAndView();
        model.addObject("role", ROLE.GUEST);
        model.setViewName("about");
        return model;
    }
}
